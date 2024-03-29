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
import priv.zhou.module.system.menu.domain.dao.MenuDAO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.query.UserQuery;

import java.util.Set;

import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;
import static priv.zhou.module.system.role.service.IRoleService.ROOT_KEY;

/**
 * Realm中不可注入Service层，
 * 因为Shiro会在Spring之前创建配置，配置中需要Realm.所以注入的Service将不被不会被所有BeanPostProcessor处理，导致事务等spring功能失效
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取授权
        UserPrincipal userPrincipal = (UserPrincipal) principals.getPrimaryPrincipal();
        Set<String> roleKeySet = roleDAO.keySet(userPrincipal.getId());
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(roleKeySet);
        if (roleKeySet.contains(ROOT_KEY)) {
            authInfo.addStringPermission("*");
        } else {
            authInfo.setStringPermissions(menuDAO.keySet(new MenuQuery(ADMIN_FLAG).setUserId(userPrincipal.getId())));
        }
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
