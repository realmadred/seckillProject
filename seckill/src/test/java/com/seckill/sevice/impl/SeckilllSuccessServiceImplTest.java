package com.seckill.sevice.impl;

import com.seckill.sevice.SeckillSuccessService;
import com.seckill.util.SeckillUtis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Madrid on 2016/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring_dao.xml"
        ,"classpath:spring_service.xml"
        ,"classpath:spring_web.xml"})
public class SeckilllSuccessServiceImplTest {

    @Resource
    private SeckillSuccessService seckillSuccessService;

    @Test
    public void testGetById() throws Exception {
        System.out.println(seckillSuccessService.getById(1));
    }

    @Test
    public void testGetAll() throws Exception {
        System.out.println(seckillSuccessService.getAll());
    }

    @Test
    public void testExcution() throws Exception {
        seckillSuccessService.excution(7,"13639948986", SeckillUtis.getMD5(7));
    }

    @Test
    public void testExcutionProcedure() throws Exception {
        System.out.println(seckillSuccessService.excutionProcedure(7,"18839948888", SeckillUtis.getMD5(7)));
    }


}