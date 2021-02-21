package priv.zhou.module.system.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.menu.domain.dao.MenuDAO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuTableVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.dao.RoleMenuDAO;

import java.util.List;
import java.util.Set;

import static priv.zhou.common.constant.RedisConst.BS_MENU_KEY;
import static priv.zhou.common.constant.RedisConst.BS_MENU_MODIFIED_KEY;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService {

    private final MenuDAO menuDAO;

    private final RoleMenuDAO roleMenuDAO;


    @Override
    public Result<NULL> save(MenuDTO menuDTO) {

        if (menuDAO.count(new MenuQuery().setName(menuDTO.getName()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType() && StringUtils.isNotBlank(menuDTO.getKey()) &&
                menuDAO.count(new MenuQuery().setKey(menuDTO.getKey()).setFlag(menuDTO.getFlag()).setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        MenuPO menuPO = new MenuPO()
                .setParentId(menuDTO.getParentId())
                .setKey(menuDTO.getKey())
                .setName(menuDTO.getName())
                .setIcon(menuDTO.getIcon())
                .setPath(menuDTO.getPath())
                .setSort(menuDTO.getSort())
                .setFlag(menuDTO.getFlag())
                .setState(menuDTO.getState())
                .setCreateBy(ShiroUtil.getUserId());
        if (menuDAO.save(menuPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (SERVICE_FLAG.equals(menuDTO.getFlag())) {
            // 4.移除服务端缓存
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }

        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> remove(Integer id) {
        MenuPO menuPO = menuDAO.get(new MenuQuery().setId(id));
        if (null == menuPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        } else if (menuDAO.removeTree(id) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        roleMenuDAO.term();
        clearCache(menuPO);
        return Result.success();
    }

    @Override
    public Result<NULL> update(MenuDTO menuDTO) {

        MenuPO menuDB = menuDAO.get(new MenuQuery()
                .setId(menuDTO.getId())
                .setFlag(menuDTO.getFlag()));
        if (null == menuDB) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        } else if (menuDAO.count(new MenuQuery()
                .setName(menuDTO.getName())
                .setRidId(menuDTO.getId())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (0 != menuDTO.getType()
                && menuDAO.count(new MenuQuery()
                .setRidId(menuDTO.getId())
                .setKey(menuDTO.getKey())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuDTO.getParentId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        MenuPO menuPO = new MenuPO()
                .setParentId(menuDTO.getParentId())
                .setKey(menuDTO.getKey())
                .setName(menuDTO.getName())
                .setIcon(menuDTO.getIcon())
                .setPath(menuDTO.getPath())
                .setSort(menuDTO.getSort())
                .setFlag(menuDTO.getFlag())
                .setState(menuDTO.getState())
                .setModifiedBy(ShiroUtil.getUserId());
        if (menuDAO.update(menuPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }
        clearCache(menuPO);
        return Result.success();
    }

    @Override
    public MenuVO getVO(MenuQuery query) {
        return menuDAO.getVO(query);
    }

    @Override
    public List<MenuTableVO> listTableVO(MenuQuery query) {
        return menuDAO.listTableVO(query);
    }

    @Override
    public Set<String> keySet(MenuQuery query) {
        return menuDAO.keySet(query);
    }


    private void clearCache(MenuPO menuPO) {
        if (ADMIN_FLAG.equals(menuPO.getFlag())) {
            ShiroUtil.clearPermissionAuthorization(menuPO.getKey());
        } else if (SERVICE_FLAG.equals(menuPO.getFlag())) {
            RedisUtil.delete(BS_MENU_KEY);
            RedisUtil.delete(BS_MENU_MODIFIED_KEY);
        }
    }

}
