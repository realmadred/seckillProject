package com.seckill.exception;

/**
 * Created by Madrid on 2016/6/16.
 */
public class SeckillRepeatException extends SeckillException {
    public SeckillRepeatException() {
    }

    public SeckillRepeatException(String message) {
        super(message);
    }

    public SeckillRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillRepeatException(Throwable cause) {
        super(cause);
    }

}
