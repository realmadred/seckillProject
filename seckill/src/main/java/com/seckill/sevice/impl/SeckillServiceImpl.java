package com.seckill.sevice.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.cache.SeckillRedisDao;
import com.seckill.entity.Seckill;
import com.seckill.entity.dto.SeckillDetail;
import com.seckill.sevice.SeckillService;
import com.seckill.util.Constants;
import com.seckill.util.SeckillUtis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Madrid on 2016/6/11.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private static final Logger log = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Resource
    private SeckillDao seckillDao;

    @Resource
    private SeckillRedisDao seckillRedisDao;

    @Override
    public List<Seckill> getAll() {
        return seckillDao.getAll();
    }

    @Override
    public Seckill getById(int id) {
        Seckill seckill = seckillRedisDao.getSeckill2(id);
        if(seckill == null){
            seckill = seckillDao.getSeckill(id);
            if(seckill != null){
                seckillRedisDao.putSeckill(seckill);
            }
        }
        return seckill;
    }

    @Override
    public SeckillDetail getSeckillDetail(Seckill seckill,int id){

        SeckillDetail seckillDetail = new SeckillDetail();//传输对象
        Integer remain = seckill == null ? 0 : seckill.getNumber();

        //设置属性
        seckillDetail.setName(seckill.getName());
        seckillDetail.setPrice(seckill.getPrice());
        seckillDetail.setNumber(remain);

        if (remain == null || remain <= 0) {
            log.debug("已经没有商品了...");
            //秒杀已经结束
            seckillDetail.setState(Constants.SECKILL_STATE_END);
            return seckillDetail;
        }

        //还有库存
        //查询商品
        //Seckill seckill = seckillService.getById(id);
        //获取当前系统时间
        long now = System.currentTimeMillis();
        //获取秒杀开始时间
        long start = seckill.getStarttime().getTime();
        //获取秒杀结束时间
        long end = seckill.getEndtime().getTime();
        //判断
        judgeSeckill(id, seckillDetail, now, start, end);
        return seckillDetail;
    }

    @Override
    public SeckillDetail getSeckillDetail(int id,Integer num) {
        SeckillDetail seckillDetail = new SeckillDetail();//传输对象

        //还有库存
        //查询商品
        Seckill seckill = seckillRedisDao.getSeckill2(id);
        if(seckill == null){
            seckill = seckillDao.getSeckill(id);
            if(seckill != null){
                seckillRedisDao.putSeckill2(seckill);
            }else {
                log.debug("已经没有商品了...");
                //秒杀已经结束
                seckillDetail.setState(Constants.SECKILL_STATE_END);
                return seckillDetail;
            }
        }
        //获取当前系统时间
        long now = System.currentTimeMillis();
        //获取秒杀开始时间
        long start = seckill.getStarttime().getTime();
        //获取秒杀结束时间
        long end = seckill.getEndtime().getTime();
        //设置属性
        seckillDetail.setName(seckill.getName());
        seckillDetail.setPrice(seckill.getPrice());
        seckillDetail.setNumber(num);
        if (num == null || num <= 0) {
            log.debug("已经没有商品了...");
            //秒杀已经结束
            seckillDetail.setState(Constants.SECKILL_STATE_END);
            return seckillDetail;
        }
        //判断
        judgeSeckill(id, seckillDetail, now, start, end);
        return seckillDetail;
    }

    //判断商品信息
    private void judgeSeckill(int id, SeckillDetail seckillDetail, long now, long start, long end) {
        if (start > now) {
            //还没有开始秒杀
            seckillDetail.setState(Constants.SECKILL_STATE_NO_START);
            //传递还需要多久开始
            seckillDetail.setDiffTime(start - now);
        } else if (end >= now) {
            //开始了还没有结束
            seckillDetail.setState(Constants.SECKILL_STATE_START);
            //暴露秒杀地址
            seckillDetail.setMd5(SeckillUtis.getMD5(id));
            seckillDetail.setDiffTime(end - now);//还有多久结束
        } else {
            //已经结束
            seckillDetail.setState(Constants.SECKILL_STATE_END);
        }
    }
}
