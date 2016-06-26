
DROP PROCEDURE IF EXISTS excute_seckill;

#分隔符
DELIMITER $$

#定义存储过程
CREATE PROCEDURE excute_seckill
  (IN v_seckill_id BIGINT,
   IN v_phone VARCHAR(11),
   IN v_secTime TIMESTAMP,
   OUT v_result INT)
#   开始
BEGIN
#   定义变量
  DECLARE insert_count INT DEFAULT 0;
  DECLARE select_result INT DEFAULT 0;
#   开启事务
  START TRANSACTION;

#   查询
  SELECT 1 FROM seckillsuccess
    WHERE phone = v_phone
    AND seckill_id = v_seckill_id
  INTO select_result;

#   判断
  IF (select_result = 1) THEN
    SET v_result = 3;#重复秒杀
  ELSE

#   插入
    INSERT INTO seckillsuccess(
      seckill_id,phone,secTime,state
    )
    VALUES (v_seckill_id,v_phone,v_secTime,1);
    #   使用row_count()获取修改的行数 row_count() < 0 sql错误 = 0 没有修改 >0 修改的行数
    SELECT row_count() INTO insert_count;

#   判断
    IF (insert_count <> 1) THEN
      ROLLBACK ;
      SET v_result = 2;#秒杀失败
    ELSE
#   插入成功

#   更新库存
    UPDATE seckill SET
      seckill.number = seckill.number - 1
      WHERE number > 0
      AND seckill.starttime < v_secTime
      AND seckill.endtime > v_secTime
      AND id = v_seckill_id;
      #   使用row_count()获取修改的行数
      SELECT row_count() INTO insert_count;
#   判断
    IF (insert_count = 1) THEN
#       秒杀成功
      SET v_result = 1;#秒杀成功
#       提交
      COMMIT;
    ELSE
      ROLLBACK ;
      SET v_result = 4;#秒杀结束
    END IF;

    END IF;
  END IF;
END $$

DELIMITER ;

SET @v_result = -3;
CALL excute_seckill(7,'18939948899',now(),@v_result);
SELECT @v_result;