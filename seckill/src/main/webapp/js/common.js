/**
 * Created by Madrid on 2016/6/12.
 */
var basePath = "http://localhost:8080/seckillContext/";
//常量

// 秒杀是否开始的状态
var SECKILL_STATE_NO_START = 0;
var SECKILL_STATE_START = 1;
var SECKILL_STATE_END = 2;

//显示时间的类型
var TIME_TO_START = 0;
var TIME_TO_END = 1;

//时间格式化样式
var TIME_FORMAT_START = '还要 %D 天 %H 时 %M 分 %S 秒 开始秒杀，请做好准备！';
var TIME_FORMAT_END = '还有 %D 天 %H 时 %M 分 %S 秒 结束！';

// 秒杀状态
var SECKILL_SUCCESS_NO_START = 0;
var SECKILL_SUCCESS_SUCCESS = 1;
var SECKILL_SUCCESS_FAILD = 2;
var SECKILL_SUCCESS_REPEAT = 3;
var SECKILL_SUCCESS_END = 4;