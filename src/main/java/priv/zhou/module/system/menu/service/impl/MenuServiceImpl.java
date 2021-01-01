package priv.zhou.module.system.menu.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutEnum;
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

import static priv.zhou.common.misc.Const.BS_MENU_KEY;
import static priv.zhou.common.misc.Const.BS_MENU_MODIFIED_KEY;

@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuDAO menuDAO;

    private final RoleDAO roleDAO;

    public MenuServiceImpl(MenuDAO menuDAO, RoleDAO roleDAO) {
        this.menuDAO = menuDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public Result<NULL> save(MenuDTO menuDTO) {


        // 1.验证参数
        if (menuDAO.count(new MenuDTO().setName(menuDTO.getName()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(OutEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType() && StringUtils.isNotBlank(menuDTO.getKey()) &&
                menuDAO.count(new MenuDTO().setKey(menuDTO.getKey()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(OutEnum.EXIST_KEY);
        }


        // 2.转换类型
        MenuPO menuPO = menuDTO.toPO()
                .setCreateId(ShiroUtil.getUserId());

        // 3.保存
        if (menuDAO.save(menuPO) < 1) {
            return Result.fail(OutEnum.FAIL_OPERATION);
        } else if (SERVICE_FLAG.equals(menuDTO.getFlag())) {
            // 4.移除服务端缓存
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> remove(MenuDTO menuDTO) {
        if (null == menuDTO.getId()) {
            return Result.fail(OutEnum.EMPTY_PARAM);
        }

        MenuPO menuPO = menuDAO.get(menuDTO);
        if (null == menuPO) {
            return Result.fail(OutEnum.EMPTY_DATA);
        } else if (menuDAO.remove(menuDTO) < 1 || roleDAO.clearMenu(new RoleDTO()) < 1) {
            throw new GlobalException().setResult(Result.fail(OutEnum.FAIL_OPERATION));
        } else if (SERVICE_FLAG.equals(menuPO.getFlag())) {
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        // 刷新权限
        ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        return Result.success();
    }

    @Override
    public Result<NULL> update(MenuDTO menuDTO) {


        // 1.验证参数
        MenuPO menuDB = menuDAO.get(new MenuDTO()
                .setId(menuDTO.getId())
                .setFlag(menuDTO.getFlag()));
        if (null == menuDB) {
            return Result.fail(OutEnum.EMPTY_DATA);
        } else if (menuDAO.count(new MenuDTO()
                .setName(menuDTO.getName())
                .setExclId(menuDTO.getId())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0
        ) {
            return Result.fail(OutEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType()
                && menuDAO.count(new MenuDTO()
                .setExclId(menuDTO.getId())
                .setKey(menuDTO.getKey())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(OutEnum.EXIST_KEY);
        }

        // 2.补充参数
        MenuPO menuPO = menuDTO.toPO()
                .setModifiedId(ShiroUtil.getUserId());

        // 3.修改菜单
        if (menuDAO.update(menuPO) < 1) {
            return Result.fail(OutEnum.FAIL_OPERATION);
        } else if (!menuDB.getKey().equals(menuPO.getKey())) {
            ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        }

        // 4.移除服务端缓存
        if (SERVICE_FLAG.equals(menuDTO.getFlag())) {
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        return Result.success();
    }

    @Override
    public Result<MenuDTO> get(MenuDTO menuDTO) {
        MenuPO menuPO = menuDAO.get(menuDTO);
        if (null == menuPO) {
            return Result.fail(OutEnum.EMPTY_DATA);
        }
        return Result.success(new MenuDTO(menuPO));
    }

    @Override
    public Result<List<MenuDTO>> list(MenuDTO menuDTO) {
        return Result.success(DTO.ofPO(menuDAO.list(menuDTO), MenuDTO::new));
    }

    @Override
    public Set<String> keySet(MenuDTO menuDTO) {
        return menuDAO.keySet(menuDTO);
    }


}
