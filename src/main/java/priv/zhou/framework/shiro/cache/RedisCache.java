package priv.zhou.framework.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.crazycake.shiro.exception.PrincipalIdNullException;
import org.crazycake.shiro.exception.PrincipalInstanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zhou.common.tools.RedisUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static priv.zhou.common.constant.ShiroConst.CACHE_PRINCIPAL_FILE;

/**
 * @author: wangsaichao
 * @date: 2018/6/22
 * @description: 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private String keyPrefix = "";
    private int expire = 0;
    private String principalIdFieldName = CACHE_PRINCIPAL_FILE;

    /**
     * Construction
     *
     * @param redisManager
     */
    public RedisCache(String prefix, int expire, String principalIdFieldName) {
        if (prefix != null && !"".equals(prefix)) {
            this.keyPrefix = prefix;
        }
        if (expire != -1) {
            this.expire = expire;
        }
        if (principalIdFieldName != null && !"".equals(principalIdFieldName)) {
            this.principalIdFieldName = principalIdFieldName;
        }
    }

    @Override
    public V get(K key) throws CacheException {
        logger.debug("get key [{}]", key);

        if (key == null) {
            return null;
        }

        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = RedisUtil.get(redisCacheKey);
            if (rawValue == null) {
                return null;
            }
            V value = (V) rawValue;
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("put key [{}]", key);
        if (key == null) {
            logger.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        } else if (value == null) {
            return value;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);

            RedisUtil.set(redisCacheKey, value, expire);
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("remove key [{}]", key);
        if (key == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = RedisUtil.get(redisCacheKey);
            V previous = (V) rawValue;
            RedisUtil.delete(redisCacheKey);
            return previous;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    private String getRedisCacheKey(K key) {
        if (key == null) {
            return null;
        }
        return this.keyPrefix + getStringRedisKey(key);
    }

    private String getStringRedisKey(K key) {
        String redisKey;
        if (key instanceof PrincipalCollection) {
            redisKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
            redisKey = key.toString();
        }
        return redisKey;
    }

    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
        String redisKey;
        Object principalObject = key.getPrimaryPrincipal();
        Method pincipalIdGetter = null;
        Method[] methods = principalObject.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (CACHE_PRINCIPAL_FILE.equals(this.principalIdFieldName)
                    && ("getAuthCacheKey".equals(m.getName()) || "getId".equals(m.getName()))) {
                pincipalIdGetter = m;
                break;
            }
            if (m.getName().equals("get" + this.principalIdFieldName.substring(0, 1).toUpperCase() + this.principalIdFieldName.substring(1))) {
                pincipalIdGetter = m;
                break;
            }
        }
        if (pincipalIdGetter == null) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
        }

        try {
            Object idObj = pincipalIdGetter.invoke(principalObject);
            if (idObj == null) {
                throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
            }
            redisKey = idObj.toString();
        } catch (IllegalAccessException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
        } catch (InvocationTargetException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
        }

        return redisKey;
    }


    @Override
    public void clear() throws CacheException {
        logger.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = RedisUtil.keys(this.keyPrefix + "*");
        } catch (Exception e) {
            logger.error("get keys error", e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        RedisUtil.delete(keys);
    }

    @Override
    public int size() {
        try {
            return RedisUtil.keys(this.keyPrefix + "*").size();
        } catch (Exception e) {
            logger.error("get keys error", e);
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        Set<String> keys = null;
        try {
            keys = RedisUtil.keys(this.keyPrefix + "*");
        } catch (Exception e) {
            logger.error("get keys error", e);
            return Collections.emptySet();
        }

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        Set<K> convertedKeys = new HashSet<K>();
        for (String key : keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                logger.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }

    @Override
    public Collection<V> values() {
        Set<String> keys = null;
        try {
            keys = RedisUtil.keys(this.keyPrefix + "*");
        } catch (Exception e) {
            logger.error("get values error", e);
            return Collections.emptySet();
        }

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        List<V> values = new ArrayList<V>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) RedisUtil.get(key);
            } catch (Exception e) {
                logger.error("deserialize values= error", e);
            }
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
