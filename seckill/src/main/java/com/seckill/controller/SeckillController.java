package com.seckill.controller;

import com.seckill.entity.Seckill;
import com.seckill.entity.dto.SeckillDetail;
import com.seckill.enumEntity.SeckillEnum;
import com.seckill.exception.SeckillEndException;
import com.seckill.exception.SeckillIllegalException;
import com.seckill.exception.SeckillRepeatException;
import com.seckill.sevice.SeckillService;
import com.seckill.sevice.SeckillSuccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Madrid on 2016/6/11.
 * 与秒杀相关的控制器
 */
@Controller//加入spring
@RequestMapping("/seckill")// url:  /模块/资源/{id}细分
public class SeckillController {

    private final static Logger log = LoggerFactory.getLogger(SeckillController.class);

    @Resource
    private SeckillService seckillService;
    @Resource
    private SeckillSuccessService seckillSuccessService;

    //商品id对应的商品
    //private Map<Integer, Seckill> seckillNumbers = new HashMap<Integer, Seckill>();
    private Map<Integer, Integer> idNumbers = new HashMap<Integer, Integer>();

    //秒杀商品列表
    private List<Seckill> list = new ArrayList<Seckill>();

    //刷新
    private void refresh() {
        //获取所有秒杀商品id对应的库存
        list = seckillService.getAll();
        idNumbers.clear();
        for (Seckill seckill : list) {
            idNumbers.put(seckill.getId(), seckill.getNumber());
        }
    }

    //刷新
    private void refresh(int id) {
        //获取所有秒杀商品id对应的库存
        Seckill seck = seckillService.getById(id);
        idNumbers.put(id,seck.getNumber());
        for (Seckill seckill : list) {
            if(seckill.getId() == id){
                seckill.setNumber(seck.getNumber());
            }
        }
    }


    //获取所有秒杀商品的列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody//json
    public List<Seckill> getList() {
        log.debug("getList...");
       // if (list == null || list.isEmpty()) {
        refresh();
        //}
        //List<Seckill> list = seckillService.getAll();
        return list;
    }

    //根据id查询秒杀商品的详细信息
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody//json
    public SeckillDetail getSeckillById(@PathVariable("id") int id) {
        log.debug("getSeckillById...");

        //首先去map里面看该商品是否存在且还有库存
        Integer num = idNumbers.get(id);
       return seckillService.getSeckillDetail(id,num);
    }

    //获取系统时间
    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    @ResponseBody
    public void getTime() {
        log.info("getTime...");
    }

    //去往秒杀商品详情页面
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String goDetail(@PathVariable("id") int id) {
        log.info("id: " + id);
        return "seckillDetail";
    }

    //执行秒杀
    @RequestMapping(value = "/excution/{id}/{phone}/{md5}", method = RequestMethod.POST)
    @ResponseBody
    public int excution(@PathVariable("id") int seckill_id, @PathVariable("phone") String phone
            , @PathVariable("md5") String md5) {
        log.info("excution...");
//        try {
//            seckillSuccessService.excution(seckill_id, phone, md5);
//        }catch (SeckillEndException e){
//            return SeckillEnum.END.getState();
//        }catch (SeckillRepeatException e){
//            return SeckillEnum.REPEAT.getState();
//        }catch (SeckillIllegalException e){
//            return SeckillEnum.NO_START.getState();
//        }catch (Exception e){
//            return SeckillEnum.FAILD.getState();
//        }
//        //秒杀成功
//        refresh();
//        return SeckillEnum.SUCCESS.getState();
        //使用存储过程执行秒杀业务
        int result = seckillSuccessService.excutionProcedure(seckill_id, phone, md5);
        if(result == SeckillEnum.SUCCESS.getState()){
            refresh(seckill_id);
        }
        return result;
    }
}
