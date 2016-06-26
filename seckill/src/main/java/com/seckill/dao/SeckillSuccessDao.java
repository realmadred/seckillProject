package com.seckill.dao;

import com.seckill.entity.SeckillSuccess;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Madrid on 2016/6/11.
 */
public interface SeckillSuccessDao {

    //根据id查询秒杀成功商品
    SeckillSuccess getSeckillSuccess(int id);

    //查询所有的秒杀成功商品
    List<SeckillSuccess> getAll();

    //根据手机号和商品id查询商品
    List<SeckillSuccess> getByPhoneAndSeckillId(@Param("seckill_id") int seckill_id,
                                          @Param("phone") String phone);

    //保存数据
    int save(@Param("seckill_id") int seckill_id, @Param("phone") String phone,
                        @Param("secTime") Date secTime, @Param("state") int state);


    void seckillProcedure(Map<String,Object> map);
}
