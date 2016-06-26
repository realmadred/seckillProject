package com.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Madrid on 2016/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring_dao.xml"})
public class SeckillSuccessDaoTest {

    //注入
    @Resource
    private SeckillSuccessDao seckillSuccessDao;
    @Test
    public void testGetSeckillSuccess() throws Exception {
        System.out.println(seckillSuccessDao.getSeckillSuccess(1));
    }

    @Test
    public void testGetAll() throws Exception {
        System.out.println(seckillSuccessDao.getAll());
    }

    @Test
    public void testGetByPhoneAndSeckillId() throws Exception {
        System.out.println(seckillSuccessDao.getByPhoneAndSeckillId(9,"13639948989"));
    }

    @Test
    public void testSave() throws Exception {
        System.out.println(seckillSuccessDao.save(8,"13639949999",new Date(),1));
    }

    @Test
    public void testSeckillProceduce(){
        Map<String,Object> map = new HashMap<>();
        map.put("seckill_id",7);
        map.put("phone","18939948888");
        map.put("secTime",new Date());
        map.put("result",null);
        seckillSuccessDao.seckillProcedure(map);
        System.out.println(map);
    }

}