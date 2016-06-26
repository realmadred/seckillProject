package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;
import com.seckill.util.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Madrid on 2016/6/20.
 * 使用redis
 */
public class SeckillRedisDao {

    private static final Logger logger = LoggerFactory.getLogger(SeckillRedisDao.class);

    //redis连接池
    private final JedisPool pool;

    //protostuff
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //字符集
    private static final String UTF8 = "UTF-8";

    //key的前缀
    private static final String KEY_PRO = "seckill:";

    //构造方法
    public SeckillRedisDao(String ip, int port) {
        super();
        pool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(int id) {
        Jedis jedis = null;
        try {
            //获取redis对象
            jedis = pool.getResource();
            //key规范
            String key = getKey(id);
            /*需要序列化
            *https://github.com/eishay/jvm-serializers/wiki
            * 采用自己的序列化方法protostuff
            */
            //创建一个空对象
            Seckill seckill = schema.newMessage();
            //获取二进制
            byte[] bytes = jedis.get(key.getBytes(UTF8));
            if (bytes != null) {
                //使用工具类,反序列化，速度很快
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                return seckill;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
            }
        }
        return null;
    }

    public Seckill getSeckill2(int id) {
        try {
            //key规范
            String key = getKey(id);
            /*需要序列化
            *https://github.com/eishay/jvm-serializers/wiki
            * 采用自己的序列化方法protostuff
            */
            //使用工具类,反序列化，速度很快
            return JedisUtils.get(key,Seckill.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        Jedis jedis = null;
        try {
            //序列化
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            String key = getKey(seckill);

            //超时缓存
            int timeout = 60 * 60;//一个小时
            jedis = pool.getResource();
            String result = jedis.setex(key.getBytes(UTF8), timeout, bytes);
            return result;//成功 "ok"
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
            }
        }
        return "";
    }

    //使用自己封装的工具类
    public String putSeckill2(Seckill seckill) {
        try {
            //序列化
            String key = getKey(seckill);
            //超时缓存
            int timeout = 60 * 60;//一个小时
            String result = JedisUtils.set(key,seckill,timeout);
            return result;//成功 "ok"
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }


    private String getKey(int id) {
        return KEY_PRO + id;
    }

    private String getKey(Seckill seckill) {
        return KEY_PRO + seckill.getId();
    }
}
