package com.seckill.util;

import com.seckill.entity.Seckill;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Madrid on 2016/6/21.
 */
public class JedisUtilsTest {

    private String key = "key:3";

    @Test
    public void testSet() throws Exception {
        Seckill seckill = new Seckill();
        seckill.setId(3);
        seckill.setNumber(3);
        seckill.setPrice(50.3);
        seckill.setName("ipad3");
        seckill.setStarttime(new Date());
        seckill.setEndtime(new Date(System.currentTimeMillis() + 1000L * 60 * 60));
        JedisUtils.set(key, seckill);
    }

    @Test
    public void testGet() throws Exception {
        System.out.print(JedisUtils.get(key, Seckill.class));
    }
}