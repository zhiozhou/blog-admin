package priv.zhou.common.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import priv.zhou.framework.shiro.UserRealm;
import priv.zhou.module.system.user.domain.dto.UserDTO;

/**
 * shiro 工具类
 *
 * @author zhou
 * @since 2020.04.09
 */
public class ShiroUtil {

    public static UserRealm getUserRealm() {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        return (UserRealm) securityManager.getRealms().iterator().next();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static UserDTO getUser() {
        return (UserDTO) getSubject().getPrincipal();
    }

    public static Integer getUserId() {
        return getUser().getId();
    }


}
