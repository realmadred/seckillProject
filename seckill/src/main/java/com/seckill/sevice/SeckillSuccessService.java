package com.seckill.sevice;

import com.seckill.entity.SeckillSuccess;
import com.seckill.enumEntity.SeckillEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Madrid on 2016/6/11.
 */
public interface SeckillSuccessService {

    SeckillSuccess getById(int id);

    List<SeckillSuccess> getAll();

    void excution(int seckill_id, String phone, String md5);

    int excutionProcedure(int seckill_id, String phone, String md5);
}
