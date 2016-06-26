package com.seckill.exception;

/**
 * Created by Madrid on 2016/6/16.
 */
public class SeckillIllegalException extends SeckillException {
    public SeckillIllegalException() {
    }

    public SeckillIllegalException(String message) {
        super(message);
    }

    public SeckillIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillIllegalException(Throwable cause) {
        super(cause);
    }
}
