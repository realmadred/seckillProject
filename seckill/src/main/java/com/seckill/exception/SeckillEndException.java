package com.seckill.exception;


/**
 * Created by Madrid on 2016/6/16.
 * 秒杀结束异常
 */
public class SeckillEndException extends SeckillException{

    public SeckillEndException() {
    }

    public SeckillEndException(String message) {
        super(message);
    }

    public SeckillEndException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillEndException(Throwable cause) {
        super(cause);
    }

}
