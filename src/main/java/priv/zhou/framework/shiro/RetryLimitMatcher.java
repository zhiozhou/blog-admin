package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

import static priv.zhou.common.constant.ShiroConst.LOGIN_ATTEMPT_LIMIT;


public class RetryLimitMatcher extends HashedCredentialsMatcher {

    private final Cache<String, AtomicInteger> retryCache;

    public RetryLimitMatcher(CacheManager cacheManager) {
        retryCache = cacheManager.getCache("retryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo authInfo) {

        String username = (String) token.getPrincipal();

        // 1.验证登录次数
        AtomicInteger retryCount = retryCache.get(username);
        if (null == retryCount) {
            retryCount = new AtomicInteger(1);
            retryCache.put(username, retryCount);
        } else if (retryCount.incrementAndGet() > LOGIN_ATTEMPT_LIMIT) {
            throw new LockedAccountException();
        }

        // 2.使用父级匹配
        boolean result = super.doCredentialsMatch(token, authInfo);
        if (result) {
            // 成功登录，清除缓存
            retryCache.remove(username);
        }
        return result;
    }


}
