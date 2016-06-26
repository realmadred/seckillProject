package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Madrid on 2016/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring_dao.xml"})
public class SeckillDaoTest {

    @Resource//注入
    private SeckillDao seckillDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSeckill() throws Exception {
        Seckill seckill = seckillDao.getSeckill(5);
        System.out.println(seckill);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Seckill> all = seckillDao.getAll();
        System.out.println(all);
    }

    @Test
    public void testDescSeckill(){
        seckillDao.descSeckillNumber(6);
    }
}