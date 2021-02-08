package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import priv.zhou.module.system.menu.controller.MenuController;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.po.UserPO;

import static java.util.Objects.isNull;
import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取授权
        UserDTO userDTO = (UserDTO) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        authInfo.setRoles(roleService.keySet(userDTO.getId()));
        authInfo.setStringPermissions(menuService.keySet(new MenuDTO().setUserId(userDTO.getId()).setFlag(ADMIN_FLAG)));
        return authInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取账户信息
        String username = (String) token.getPrincipal();
        UserPO userPO = userDAO.get(new UserDTO().setUsername(username).setState(0));
        if (null == userPO) {
            return null;
        }

        // todo 验证用户状态

        return new SimpleAuthenticationInfo(new UserDTO(userPO), userPO.getPassword(), new CacheByteSource(userPO.getSalt()), getName());
    }


    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
