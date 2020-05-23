package com.own.test.service.impl;

import com.own.test.to.SecKillTo;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/*
 * @author  zf
 * @date  2020/5/23 3:28 下午
 */
@Service
public class SecKillServiceImpl {

    public static final String SECKILL_INFO = "seckill_info";
    public static final String SECKILL_SEMAPHORE = "seckill_info:";
    public static final String SECKILL_ISKILL = "seckill_iskill:";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Redisson redisson;

    /**
     * 秒杀处理:将秒杀成功下单处理异步交给消息队列处理，秒杀系统只做前期的判断，以便提高并发
     *
     * @param killId 秒杀id
     * @param skuId  商品规格id(商品id)
     * @param userId 暂时先传入，后期放入分布式session中
     * @param num    秒杀数量
     */
    public void secKillProuct(String killId, String skuId, String userId, Integer num) {

        //通过killId去redis中查询对应的秒杀商品信息
        Object value = redisTemplate.boundHashOps(SECKILL_INFO).get(killId);
        if (null != value) {
            SecKillTo secKillTo = (SecKillTo) value;
            //秒杀的商品id是否为当前商品id
            if (killId.equals(secKillTo.getKillId()) && skuId.equals(secKillTo.getSkuId())) {
                //判断当前秒杀的商品是否过期
                long nowTime = System.currentTimeMillis();
                if (nowTime <= secKillTo.getSeckillEndTime().getTime() && nowTime >= secKillTo.getSeckillStartTime().getTime()) {
                    //判断该用户是否秒杀过
                    long ttl = secKillTo.getSeckillEndTime().getTime() - secKillTo.getSeckillStartTime().getTime();
                    String isKillKey = SECKILL_ISKILL + killId + ":" + userId;
                    Boolean isKilledFlag = redisTemplate.opsForValue().setIfAbsent(isKillKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                    if (isKilledFlag) {
                        //秒杀的数量是否超过剩余库存数量
                        RSemaphore semaphore = redisson.getSemaphore(SECKILL_SEMAPHORE + killId);
                        boolean flag = semaphore.tryAcquire(num);
                        if (flag) {
                            //秒杀成功后，放入消息队列中去消费，提高服务器并发能力，剩下就是消息队列异步消费消息生成订单信息，页面发起支付调用

                        } else {

                        }

                    } else {
                        //后期直接抛错，全局异常捕获后返回给页面
                    }

                } else {

                }

            } else {

            }
        } else {

        }


    }
}
