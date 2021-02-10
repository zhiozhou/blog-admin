package priv.zhou.framework.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


public class UserCredentialsMatcher extends SimpleCredentialsMatcher {

    private final int maxAttempt;

    private final Cache<String, AtomicInteger> attemptCache;

    public UserCredentialsMatcher(int maxAttempt, Cache<String, AtomicInteger> attemptCache) {
        this.maxAttempt = maxAttempt;
        this.attemptCache = attemptCache;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo authInfo) {

        String username = (String) token.getPrincipal();

        // 1.验证登录次数
        AtomicInteger retryCount = attemptCache.get(username);
        if (null == retryCount) {
            retryCount = new AtomicInteger(1);
            attemptCache.put(username, retryCount);
        } else if (retryCount.incrementAndGet() > maxAttempt) {
            // 修改数据库状态未锁定，管理端增加解锁
            throw new LockedAccountException();
        }

        // 2.使用父级匹配
        PrincipalCollection principals = authInfo.getPrincipals();
//        boolean matched = match(token.getCredentials(),authInfo.)
        boolean result = super.doCredentialsMatch(token, authInfo);
        if (result) {
            // 成功登录，清除缓存
            attemptCache.remove(username);
        } else {
            // 登录失败，追加限制
            attemptCache.put(username, retryCount);
        }
        return result;
    }


    public boolean match(String hashed, String salt, String cert) {
        return cert.equals(buildCert(hashed, salt));
    }

    public String buildCert(String hashed, String salt) {
        return new Md5Hash(hashed + salt).toHex();
    }


}
