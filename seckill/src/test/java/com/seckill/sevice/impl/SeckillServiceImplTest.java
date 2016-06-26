package com.seckill.sevice.impl;

import com.seckill.sevice.SeckillService;
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
public class SeckillServiceImplTest {

    @Resource
    private SeckillService seckillService;

    @Test
    public void testGetAll() throws Exception {
        System.out.println(seckillService.getAll());
    }

    @Test
    public void testGetById() throws Exception {
        System.out.println(seckillService.getById(6));
    }
}