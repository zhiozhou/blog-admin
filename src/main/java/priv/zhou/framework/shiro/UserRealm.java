package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.query.UserQuery;

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
        UserPrincipal userPrincipal = (UserPrincipal) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(roleService.keySet(userPrincipal.getId()));
        authInfo.setStringPermissions(menuService.keySet(new MenuQuery().setUserId(userPrincipal.getId()).setFlag(ADMIN_FLAG)));
        return authInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户信息
        String username = (String) token.getPrincipal();
        UserPrincipal userPrincipal = userDAO.getPrincipal(new UserQuery().setUsername(username));
        if (null == userPrincipal) {
            return null;
        }
        return buildAuthenticationInfo(userPrincipal);
    }

    public static AuthenticationInfo buildAuthenticationInfo(UserPrincipal userPrincipal) {
        return new SimpleAuthenticationInfo(userPrincipal, userPrincipal.getPassword(), new CacheByteSource(userPrincipal.getSalt()), userPrincipal.getUsername());
    }

}
