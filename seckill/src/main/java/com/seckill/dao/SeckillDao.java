package com.seckill.dao;

import com.seckill.entity.Seckill;

import java.util.List;

/**
 * Created by Madrid on 2016/6/11.
 */
public interface SeckillDao {

    //根据id查询秒杀商品
    Seckill getSeckill(int id);

    //查询所有的秒杀商品
    List<Seckill> getAll();

    //减少库存数量
    int descSeckillNumber(int id);

}
