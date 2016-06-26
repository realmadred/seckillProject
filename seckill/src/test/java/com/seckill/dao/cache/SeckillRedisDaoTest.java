package com.seckill.dao.cache;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by Madrid on 2016/6/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring_dao.xml"})
public class SeckillRedisDaoTest {

    @Resource(name = "redisDao")
    private SeckillRedisDao dao ;

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testGetSeckill() throws Exception {
        System.out.println(dao.getSeckill(2));
    }

    @Test
    public void testPutSeckill() throws Exception {
        Seckill seckill = new Seckill();
        seckill.setId(2);
        seckill.setNumber(2);
        seckill.setPrice(300.0);
        seckill.setName("ip");
        seckill.setStarttime(Timestamp.valueOf("2016-06-20 11:10:11"));
        seckill.setEndtime(Timestamp.valueOf("2016-06-22 11:10:11"));
        System.out.println(dao.putSeckill(seckill));
    }

    @Test
    public void testRedis(){
        //首先获取seckill
        Seckill seckill = dao.getSeckill(6);
        if(seckill == null){
            seckill = seckillDao.getSeckill(6);
            if(seckill != null){
                //添加到redis
                System.out.println(dao.putSeckill(seckill));
            }
        }
        System.out.println(seckill);
    }
}