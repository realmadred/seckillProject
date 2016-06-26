package com.seckill.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Madrid on 2016/6/10.
 */
public class Seckill implements Serializable {

    private static final long serialVersionUID = 869869857847859876L;

    private Integer id;
    private String name;//商品名称
    private double price;//价格
    private Integer number;//库存
    private Date starttime;//秒杀开始时间
    private Date endtime;//秒杀结束时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}
