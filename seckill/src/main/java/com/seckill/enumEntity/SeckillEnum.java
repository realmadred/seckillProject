package com.seckill.enumEntity;

/**
 * Created by Madrid on 2016/6/10.
 */
public enum SeckillEnum {
    NO_START(0,"未开始"),
    SUCCESS(1,"成功"),
    FAILD(2,"失败"),
    REPEAT(3,"重复秒杀"),
    END(4,"结束"),
    ;

    private int state;
    private String msg;

    SeckillEnum(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg(int state) {
       for (SeckillEnum seckillEnum:values()){
           if(state == seckillEnum.getState()){
               return seckillEnum.getMsg();
           }
       }
        return "";
    }
}
