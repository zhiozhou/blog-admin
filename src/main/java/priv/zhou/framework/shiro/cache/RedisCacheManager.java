package priv.zhou.framework.shiro.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangsaichao
 * @date 2018/6/22
 * @description 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 */
@Getter
@Setter
public class RedisCacheManager implements CacheManager {


    /**
     * fast lookup by name map
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    private int expire = 1800;

    private String keyPrefix = "shiro:cache:";

    private String principalIdFieldName = "username";

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);

        if (cache == null) {
            cache = new RedisCache<K, V>( keyPrefix + name + ":", expire, principalIdFieldName);
            caches.put(name, cache);
        }
        return cache;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
