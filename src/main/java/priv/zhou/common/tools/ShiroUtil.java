package priv.zhou.common.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import priv.zhou.common.properties.ShiroProperties;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import java.util.List;

/**
 * shiro 工具类
 *
 * @author zhou
 * @since 2020.04.09
 */
public class ShiroUtil {

    public static final ShiroProperties shiroProperties = SpringUtils.getBean(ShiroProperties.class);

    public static final RedisCacheManager cacheManager = SpringUtils.getBean(RedisCacheManager.class);


    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static void stopSession() {
        getSession().stop();
    }


    public static UserPrincipal getUser() {
        return (UserPrincipal) getSubject().getPrincipal();
    }

    public static Integer getUserId() {
        return getUser().getId();
    }


    /**
     * 清除指定用户授权缓存
     */
    public static void putAuthentication(String username, AuthenticationInfo authenticationInfo) {
        cacheManager.getCache(shiroProperties.getAuthenticationCacheName()).put(username, authenticationInfo);
    }

    /**
     * 清除指定用户授权缓存
     */
    public static void clearAuthentication(String username) {
        cacheManager.getCache(shiroProperties.getAuthenticationCacheName()).remove(username);
    }


    /**
     * 批量清除用户授权缓存
     */
    public static void clearAuthentication(List<String> usernames) {
        for (String username : usernames) {
            cacheManager.getCache(shiroProperties.getAuthenticationCacheName()).remove(username);
        }
    }

    /**
     * 清除指定角色权限缓存
     */
    public static void clearRoleAuthorization(String roleName) {
        Cache<String, SimpleAuthorizationInfo> cache = cacheManager.getCache(shiroProperties.getAuthorizationCacheName());
        for (String key : cache.keys()) {
            SimpleAuthorizationInfo authorization = cache.get(key);
            if (authorization.getRoles().contains(roleName)) {
                cache.remove(key);
            }
        }
    }

    /**
     * 清除指定角色权限缓存
     */
    public static void clearPermissionAuthorization(String permission) {
        Cache<String, SimpleAuthorizationInfo> cache = cacheManager.getCache(shiroProperties.getAuthorizationCacheName());
        for (String key : cache.keys()) {
            SimpleAuthorizationInfo authorization = cache.get(key);
            if (authorization.getStringPermissions().contains(permission)) {
                cache.remove(key);
            }
        }
    }

}
