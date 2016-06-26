package com.seckill.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Madrid on 2016/6/10.
 */
public class SeckillSuccess implements Serializable {

    private static final long serialVersionUID = 23432453246564L;

    private int id;
    private Seckill seckill;//秒杀的商品
    private String phone;//电话
    private Timestamp secTime;//秒杀时间
    private Integer state;//秒杀的状态 0 为开始 1 成功 2 秒杀失败 3 秒杀结束 4 异常

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getSecTime() {
        return secTime;
    }

    public void setSecTime(Timestamp secTime) {
        this.secTime = secTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SeckillSuccess{" +
                "id=" + id +
                ", seckill=" + seckill +
                ", phone='" + phone + '\'' +
                ", secTime=" + secTime +
                ", state=" + state +
                '}'+"\n";
    }
}
