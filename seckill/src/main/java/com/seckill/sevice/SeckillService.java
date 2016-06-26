package com.seckill.sevice;

import com.seckill.entity.Seckill;
import com.seckill.entity.dto.SeckillDetail;

import java.util.List;

/**
 * Created by Madrid on 2016/6/11.
 */
public interface SeckillService {

    List<Seckill> getAll();

    Seckill getById(int id);

    SeckillDetail getSeckillDetail(Seckill seckill, int id);

    SeckillDetail getSeckillDetail(int id,Integer num);

}