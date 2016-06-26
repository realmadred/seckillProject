package com.seckill.entity.dto;

import com.seckill.enumEntity.SeckillEnum;

import java.io.Serializable;

/**
 * Created by Madrid on 2016/6/15.
 * 数据传输对象
 */
public class SeckillDetail implements Serializable {

    private static final long serialVersionUID = 69798798789768768L;


    private int state;//状态
    private String name;//商品名称
    private double price;//价格
    private Integer number;//库存
    private String md5;//md5加密数据

    public Long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(Long diffTime) {
        this.diffTime = diffTime;
    }

    private Long diffTime;//距离开始的时间差

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
