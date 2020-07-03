package priv.zhou.module.system.menu.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.blog.menu.controller.MenuController;
import priv.zhou.module.system.menu.domain.dao.MenuDAO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.BLOG_SERVICE_MENU_KEY;
import static priv.zhou.common.param.CONSTANT.BLOG_SERVICE_MENU_MODIFIED_KEY;

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
        // 1.转换类型
        MenuPO menuPO = menuDTO.toPO();


        // 2.验证参数
        if (menuDAO.count(new MenuDTO()
                .setName(menuPO.getName())
                .setFlag(menuPO.getFlag())
                .setParentId(menuPO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (0 != menuPO.getType() &&
                StringUtils.isNotBlank(menuDTO.getKey())
                && menuDAO.count(new MenuDTO()
                .setKey(menuPO.getKey())
                .setFlag(menuPO.getFlag())
                .setParentId(menuPO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }


        // 3.补充参数
        menuPO.setCreateId(ShiroUtil.getUserId());

        // 4.保存
        if(menuDAO.save(menuPO) <1){
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        // 5.移除服务端缓存
        if(MenuController.FLAG.equals(menuDTO.getFlag())){
            RedisUtil.delete(BLOG_SERVICE_MENU_KEY);
            RedisUtil.delete(BLOG_SERVICE_MENU_MODIFIED_KEY);
        }

        return OutVO.success();
    }

    @Override
    public OutVO<NULL> remove(MenuDTO menuDTO) {
        if (null == menuDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        MenuPO menuPO = menuDAO.get(menuDTO);
        if(null == menuPO){
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        }else if(MenuController.FLAG.equals(menuPO.getFlag())){
            RedisUtil.delete(BLOG_SERVICE_MENU_KEY);
            RedisUtil.delete(BLOG_SERVICE_MENU_MODIFIED_KEY);
        }

        menuDAO.remove(menuDTO);
        roleDAO.clearMenu(new RoleDTO());

        // 刷新权限
        ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(MenuDTO menuDTO) {

        // 1.转换类型
        MenuPO menuPO = menuDTO.toPO();

        // 2.验证参数
        if (menuDAO.count(new MenuDTO()
                .setName(menuPO.getName())
                .setNoid(menuPO.getId())
                .setFlag(menuDTO.getFlag())
                .setParentId(menuPO.getParentId())) > 0
        ) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (0 != menuPO.getType()
                && menuDAO.count(new MenuDTO()
                        .setKey(menuPO.getKey())
                        .setNoid(menuPO.getId())
                        .setFlag(menuDTO.getFlag())
                        .setParentId(menuPO.getParentId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        menuPO.setModifiedId(ShiroUtil.getUserId());


        // 4.修改菜单
        if( menuDAO.update(menuPO) < 1){
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        // 5.移除服务端缓存
        if(MenuController.FLAG.equals(menuDTO.getFlag())){
            RedisUtil.delete(BLOG_SERVICE_MENU_KEY);
            RedisUtil.delete(BLOG_SERVICE_MENU_MODIFIED_KEY);
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
    public OutVO<Integer> count(MenuDTO menuDTO) {
        return OutVO.success(menuDAO.count(menuDTO));
    }


    @Override
    public Set<String> keySet(MenuDTO menuDTO) {
        return menuDAO.keySet(menuDTO);
    }


}
