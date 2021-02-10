package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.po.UserPO;

import java.util.concurrent.atomic.AtomicInteger;

import static priv.zhou.common.constant.GlobalConst.ZHOU_ID;


public class UserCredentialsMatcher extends SimpleCredentialsMatcher {

    private final int maxAttempt;

    private final UserDAO userDAO;

    private final Cache<String, AtomicInteger> attemptCache;

    public UserCredentialsMatcher(UserDAO userDAO, int maxAttempt, Cache<String, AtomicInteger> attemptCache) {
        this.userDAO = userDAO;
        this.maxAttempt = maxAttempt;
        this.attemptCache = attemptCache;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo authInfo) {

        // 1.验证用户
        UserPrincipal userPrincipal = (UserPrincipal) authInfo.getPrincipals().getPrimaryPrincipal();
        if (userPrincipal.getState() == 11) {
            throw new LockedAccountException();
        } else if (userPrincipal.getState() == 12) {
            throw new DisabledAccountException();
        }

        // 2.验证登录次数
        String cacheKey = userPrincipal.getUsername();
        AtomicInteger retryCount = attemptCache.get(cacheKey);
        if (null == retryCount) {
            retryCount = new AtomicInteger(1);
            attemptCache.put(cacheKey, retryCount);
        } else if (retryCount.incrementAndGet() > maxAttempt) {
            if (userDAO.update(new UserPO()
                    .setId(userPrincipal.getId())
                    .setState(11)
                    .setModifiedBy(ZHOU_ID)) < 1) {
                throw new GlobalException(ResultEnum.LATER_RETRY);
            }
            ShiroUtil.putAuthentication(userPrincipal.getUsername(), UserRealm.buildAuthenticationInfo(userPrincipal.setState(11)));
            throw new LockedAccountException();
        }

        // 3.匹配密码
        if (match(new String((char[]) token.getCredentials()), userPrincipal.getSalt(), userPrincipal.getPassword())) {
            // 成功登录，清除缓存
            attemptCache.remove(cacheKey);
            return true;
        }

        // 登录失败，追加限制
        attemptCache.put(cacheKey, retryCount);
        return false;
    }


    public boolean match(String hashed, String salt, String cert) {
        return cert.equals(buildCert(hashed, salt));
    }

    public String buildCert(String hashed, String salt) {
        return new Md5Hash(hashed + salt).toHex();
    }


}
