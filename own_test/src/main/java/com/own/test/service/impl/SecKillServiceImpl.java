package com.own.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
 * @author  zf
 * @date  2020/5/23 3:28 下午
 */
@Service
public class SecKillServiceImpl {

    public static final String SECKILL_INFO = "seckill_info";

    @Autowired
    private RedisTemplate redisTemplate;

    public void secKillProuct(String killId, BigDecimal num){
        //通过killId去redis中查询对应的秒杀商品信息
        Object value = redisTemplate.boundHashOps(SECKILL_INFO).get(killId);
        if(null != value){

        }

        //判断当前秒杀的商品是否过期，秒杀的数量是否超过剩余库存数量，秒杀的商品id是否为当前商品id
        //验证完成，开始秒杀，保证一个用户只能秒杀一次(redis的分布式锁)
        //秒杀成功后，放入消息队列中去消费，提高服务器并发能力
        //消息队列消费消息，生城订单信息，成功发起支付接口进行支付
    }
}
