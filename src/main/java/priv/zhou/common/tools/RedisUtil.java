package priv.zhou.common.tools;


import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author zhou
 * @since 2019.08.12
 */
@SuppressWarnings("unused")
public class RedisUtil {

    @SuppressWarnings("unchecked")
    private static final RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean(RedisTemplate.class);

    /**
     * 默认时间单位
     */
    private static final TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;

    private RedisUtil() {
    }


    /**
     * 表达式获取key集合
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 秒级过期
     */
    public static void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, DEFAULT_UNIT);
    }

    /**
     * 自定义过期单位
     */
    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }


    public static Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置value并返回前一个值
     */
    public static Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 删除指定key
     *
     * @return 成功返回true，失败false
     */
    public static boolean delete(String key) {
        return ParseUtil.unBox(redisTemplate.delete(key));
    }

    /**
     * 删除key集合
     *
     * @return 受影响行数
     */
    public static Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 如果不存在则设置，返回true，存在返回false
     */
    public static boolean setIfAbsent(String key, Object value) {
        return ParseUtil.unBox(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    public static boolean setIfAbsent(String key, Object value, long timeout) {
        return ParseUtil.unBox(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, DEFAULT_UNIT));
    }

    public static boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        return ParseUtil.unBox(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit));
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public static Long incr(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 获取key过期秒数
     */
    public static Long expire(String key) {
        return redisTemplate.getExpire(key, DEFAULT_UNIT);
    }

    /**
     * Map结构
     */
    public final static class Map {

        public static Object get(String mapKey, String key) {
            return redisTemplate.opsForHash().get(mapKey, key);
        }

        public static void put(String mapKey, String key, Object value) {
            redisTemplate.opsForHash().put(mapKey, key, value);
        }

        public static void put(String mapKey, String key, Object value, long timeout) {
            put(mapKey, key, value, timeout, DEFAULT_UNIT);
        }

        public static void put(String mapKey, String key, Object value, long timeout, TimeUnit unit) {
            redisTemplate.opsForHash().put(mapKey, key, value);
            redisTemplate.expire(mapKey, timeout, unit);
        }

        public static void delete(String mapKey, Object key) {
            redisTemplate.opsForHash().delete(mapKey, key);
        }

        public boolean hasKey(String mapKey, Object key) {
            return redisTemplate.opsForHash().hasKey(mapKey, key);
        }

        public static java.util.Map<Object, Object> entries(String mapKey) {
            return redisTemplate.opsForHash().entries(mapKey);
        }

        public static Long incr(String mapKey, String key, long increment) {
            return redisTemplate.opsForHash().increment(mapKey, key, increment);
        }

        public static Long incr(String mapKey, String key, long increment, long timeout) {
            return incr(mapKey, key, increment, timeout, DEFAULT_UNIT);
        }

        public static Long incr(String mapKey, String key, long increment, long timeout, TimeUnit unit) {
            Long result = redisTemplate.opsForHash().increment(mapKey, key, increment);
            redisTemplate.expire(mapKey, timeout, unit);
            return result;
        }
    }


    /**
     * 有序Set
     */
    public final static class ZSet {

        /**
         * 获取排行榜总数
         */
        public static Long getSize(String setKey) {
            return redisTemplate.opsForZSet().zCard(setKey);
        }

        /**
         * 获取排行名次
         */
        public static Long getLevel(String setKey, String key) {
            Long level = redisTemplate.opsForZSet().reverseRank(setKey, key);
            return null == level ? null : level + 1; // 索引排行
        }

        /**
         * 获取分数
         */
        public static Double getScore(String setKey, String key) {
            return redisTemplate.opsForZSet().score(setKey, key);
        }

        /**
         * 保存元素
         */
        public static void add(String rankey, String key, Double score) {
            redisTemplate.opsForZSet().add(rankey, key, score);
        }

        /**
         * 自增分数
         */
        public static Double incrScore(String rankey, String key, Double delta) {
            return redisTemplate.opsForZSet().incrementScore(rankey, key, delta);
        }

        /**
         * 移除排行
         */
        public static Long remove(String setKey, String key) {
            return redisTemplate.opsForZSet().remove(setKey, key);
        }

        /**
         * 移除排行区间
         */
        public static Long removeRange(String setKey, long start, long end) {
            return redisTemplate.opsForZSet().removeRange(setKey, start, end);
        }

    }


}
