package com.seckill.util;

import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * Created by Madrid on 2016/6/15.
 * 秒杀的工具类
 */
public class SeckillUtis {

    //盐值，需要非常的复杂
    private final static String SALT = "df789dfg7d8s7f*&^*%&^hkjh@%$rert#%$";

    //对秒杀地址进行加密处理
    public static String getMD5(int id){
        try {
            String msg = SALT+id+SALT;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] msgByte = md.digest(msg.getBytes("UTF-8"));
            //Base64Utils.encodeToString(msgByte);
            String result = DigestUtils.md5DigestAsHex(msgByte);
            return result;
        }catch (Exception e){
            throw new RuntimeException("加密失败！");
        }
    }

    public static void main(String[] args) {
        System.out.println(getMD5(3));
    }

}
