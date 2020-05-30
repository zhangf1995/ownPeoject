package com.own.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.own.test.service.SecKillService;
import com.own.test.to.SecKillTo;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/*
 * @author  zf
 * @date  2020/5/23 3:28 下午
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    public static final String SECKILL_INFO = "seckill_info";
    public static final String SECKILL_SEMAPHORE = "seckill_semphore:";
    public static final String SECKILL_ISKILL = "seckill_iskill:";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    /**
     * 秒杀处理:将秒杀成功下单处理异步交给消息队列处理，秒杀系统只做前期的判断，以便提高并发
     *
     * @param killId 秒杀id
     * @param skuId  商品规格id(商品id)
     * @param userId 暂时先传入，后期放入分布式session中
     * @param num    秒杀数量
     */
    @Override
    public Boolean secKillProduct(String killId, String skuId, String userId, Integer num) throws Exception{

        //通过killId去redis中查询对应的秒杀商品信息
        BoundHashOperations<String,String,String> boundHashOperations = redisTemplate.boundHashOps(SecKillServiceImpl.SECKILL_INFO);
        String secKillInfo = boundHashOperations.get(killId);
        if (null != secKillInfo) {
            SecKillTo secKillTo = JSONObject.parseObject(secKillInfo, SecKillTo.class);
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
                            return true;
                        } else {
                            //delete分布式锁
                            redisTemplate.delete(isKillKey);
                            throw new Exception("库存数量不足");
                        }

                    } else {
                        //后期直接抛错，全局异常捕获后返回给页面
                        throw new Exception("您已参与过该秒杀活动，请勿重复参与");
                    }
                } else {
                    throw new Exception("该秒杀活动已过期");
                }
            } else {
                throw new Exception("秒杀商品非活动商品");
            }
        }
        return false;
    }
}
