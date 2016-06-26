package com.seckill.sevice.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SeckillSuccessDao;
import com.seckill.entity.SeckillSuccess;
import com.seckill.enumEntity.SeckillEnum;
import com.seckill.exception.SeckillEndException;
import com.seckill.exception.SeckillException;
import com.seckill.exception.SeckillIllegalException;
import com.seckill.exception.SeckillRepeatException;
import com.seckill.sevice.SeckillSuccessService;
import com.seckill.util.SeckillUtis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Madrid on 2016/6/11.
 */
@Service
public class SeckilllSuccessServiceImpl implements SeckillSuccessService {

    private final static Logger logger = LoggerFactory.getLogger(SeckilllSuccessServiceImpl.class);

    @Resource
    private SeckillSuccessDao seckillSuccessDao;
    @Resource
    private SeckillDao seckillDao;


    public SeckillSuccess getById(int id) {
        return seckillSuccessDao.getSeckillSuccess(id);
    }

    public List<SeckillSuccess> getAll() {
        logger.debug("getAll...");
        return seckillSuccessDao.getAll();
    }

    @Transactional//使用默认的事务
    public void excution(int seckill_id, String phone, String md5) {
        try {
            //检查MD5
            String md = SeckillUtis.getMD5(seckill_id);
            if(!md.equals(md5)){
                throw new SeckillIllegalException("非法秒杀！");
            }

            //检查是否重复秒杀
            List<SeckillSuccess> seckillSuccess = seckillSuccessDao.getByPhoneAndSeckillId(seckill_id, phone);
            if(seckillSuccess != null && !seckillSuccess.isEmpty()){
                throw new SeckillRepeatException("重复秒杀异常！");
            }

            //插入到成功表
            int n = seckillSuccessDao.save(seckill_id,phone,new Date(),1);
            if(n != 1){
                throw new SeckillException("秒杀失败！");
            }
            //更新库存
            n = seckillDao.descSeckillNumber(seckill_id);
            if(n<= 0){
                throw new SeckillEndException("秒杀结束！");
            }
        } catch (SeckillIllegalException e) {
            throw new SeckillIllegalException(e);
        } catch (SeckillRepeatException e) {
            throw new SeckillRepeatException(e);
        } catch (SeckillEndException e) {
            throw new SeckillEndException(e);
        } catch (Exception e) {
            throw new SeckillException(e);
        }
    }

    @Override
    //使用存储过程不需要spring的事务
    public int excutionProcedure(int seckill_id, String phone, String md5) {
        try {
            //检查MD5
            String md = SeckillUtis.getMD5(seckill_id);
            if(!md.equals(md5)){
               return SeckillEnum.NO_START.getState();//非法
            }

            //执行存储过程执行秒杀操作
            Map<String,Object> map = new HashMap<>();
            map.put("seckill_id",seckill_id);
            map.put("phone",phone);
            map.put("secTime",new Date());
            map.put("result",null);
            //执行
            seckillSuccessDao.seckillProcedure(map);
            //获取执行结果
           return Integer.valueOf(map.get("result").toString());
        } catch (Exception e) {
           return SeckillEnum.NO_START.getState();
        }
    }
}
