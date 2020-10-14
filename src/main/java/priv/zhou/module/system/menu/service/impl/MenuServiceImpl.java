package priv.zhou.module.system.menu.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.menu.domain.dao.MenuDAO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.BS_MENU_KEY;
import static priv.zhou.common.param.CONSTANT.BS_MENU_MODIFIED_KEY;

@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuDAO menuDAO;

    private final RoleDAO roleDAO;

    public MenuServiceImpl(MenuDAO menuDAO, RoleDAO roleDAO) {
        this.menuDAO = menuDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public OutVO<NULL> save(MenuDTO menuDTO) {


        // 1.验证参数
        if (menuDAO.count(new MenuDTO().setName(menuDTO.getName()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType() && StringUtils.isNotBlank(menuDTO.getKey()) &&
                menuDAO.count(new MenuDTO().setKey(menuDTO.getKey()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }


        // 2.转换类型
        MenuPO menuPO = menuDTO.toPO()
                .setCreateId(ShiroUtil.getUserId());

        // 3.保存
        if (menuDAO.save(menuPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        } else if (SERVICE_FLAG.equals(menuDTO.getFlag())) {
            // 4.移除服务端缓存
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        return OutVO.success();
    }

    @Override
    @Transactional
    public OutVO<NULL> remove(MenuDTO menuDTO) {
        if (null == menuDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        MenuPO menuPO = menuDAO.get(menuDTO);
        if (null == menuPO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        } else if (menuDAO.remove(menuDTO) < 1 || roleDAO.clearMenu(new RoleDTO()) < 1) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        } else if (SERVICE_FLAG.equals(menuPO.getFlag())) {
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        // 刷新权限
        ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(MenuDTO menuDTO) {


        // 1.验证参数
        MenuPO menuDB = menuDAO.get(new MenuDTO()
                .setId(menuDTO.getId())
                .setFlag(menuDTO.getFlag()));
        if (null == menuDB) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        } else if (menuDAO.count(new MenuDTO()
                .setName(menuDTO.getName())
                .setNoid(menuDTO.getId())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0
        ) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType()
                && menuDAO.count(new MenuDTO()
                .setNoid(menuDTO.getId())
                .setKey(menuDTO.getKey())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 2.补充参数
        MenuPO menuPO = menuDTO.toPO()
                .setModifiedId(ShiroUtil.getUserId());

        // 3.修改菜单
        if (menuDAO.update(menuPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        } else if (!menuDB.getKey().equals(menuPO.getKey())) {
            ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        }

        // 4.移除服务端缓存
        if (SERVICE_FLAG.equals(menuDTO.getFlag())) {
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        return OutVO.success();
    }

    @Override
    public OutVO<MenuDTO> get(MenuDTO menuDTO) {
        MenuPO menuPO = menuDAO.get(menuDTO);
        if (isNull(menuPO)) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }
        return OutVO.success(new MenuDTO(menuPO));
    }

    @Override
    public OutVO<List<MenuDTO>> list(MenuDTO menuDTO) {
        return OutVO.success(DTO.ofPO(menuDAO.list(menuDTO), MenuDTO::new));
    }

    @Override
    public Set<String> keySet(MenuDTO menuDTO) {
        return menuDAO.keySet(menuDTO);
    }


}
