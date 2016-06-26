package com.seckill.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Madrid on 2016/6/20.
 * jedis工具类
 */
public class JedisUtils {

    private final static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static final JedisPool pool;

    private static final String IP = "127.0.0.1";

    private static final int PORT = 6379;

    private static final String UTF8 = "UTF-8";

    //set,设置存活时间
    private static final int TIMEOUT_H = 60 * 60;//一小时;

    static {
        pool = new JedisPool(IP, PORT);
    }

    public static <T> String set(String key, T obj, int timeout) {
        logger.info("set jedis key" + obj);
        Jedis jedis = null;
        try {
            //序列化
            byte[] bytes = ProtostuffUtil.serializer(obj);
            //获取jedis
            jedis = getJedis();
            //set到数据库，有存活时间
            timeout = timeout <= 0 ? TIMEOUT_H : timeout;
            return jedis.setex(key.getBytes(UTF8), timeout, bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return "";
    }

    //使用默认的失效时间
    public static <T> String set(String key, T obj) {
        logger.info("set jedis key" + obj);
        Jedis jedis = null;
        try {
            //序列化
            byte[] bytes = ProtostuffUtil.serializer(obj);
            //获取jedis
            jedis = getJedis();
            //set到数据库，有存活时间
            return jedis.setex(key.getBytes(UTF8), TIMEOUT_H, bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return "";
    }

    public static <T> T get(String key, Class<T> clazz) {
        logger.info("get jedis key...");
        Jedis jedis = null;
        try {
            // RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
            //获取空对象
            //T o = schema.newMessage();
            jedis = getJedis();
            //从数据库查询数据
            byte[] bytes = jedis.get(key.getBytes(UTF8));
            if (bytes != null) {
                //反序列化
                // ProtostuffIOUtil.mergeFrom(bytes, o, schema);
                return ProtostuffUtil.deserializer(bytes, clazz);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    //获取jedis
    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
