package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

import static priv.zhou.common.constant.ShiroConst.LOGIN_ATTEMPT_LIMIT;
import static priv.zhou.common.constant.ShiroConst.RETRY_CACHE_NAME;


public class RetryLimitMatcher extends HashedCredentialsMatcher {

    private final Cache<String, AtomicInteger> retryCache;

    public RetryLimitMatcher(CacheManager cacheManager) {
        retryCache = cacheManager.getCache(RETRY_CACHE_NAME);
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
            // 修改数据库状态未锁定，管理端增加解锁
            throw new LockedAccountException();
        }

        // 2.使用父级匹配
        boolean result = super.doCredentialsMatch(token, authInfo);
        if (result) {
            // 成功登录，清除缓存
            retryCache.remove(username);
        } else {
            // 登录失败，追加限制
            retryCache.put(username, retryCount);
        }
        return result;
    }
}
