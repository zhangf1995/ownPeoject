package com.own.test.controller;

import com.own.common.result.Result;
import com.own.test.constant.SeckillResponse;
import com.own.test.service.SecKillService;
import com.own.test.vo.SecKillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author  zf
 * @date  2020/5/19 10:24 下午
 */
@RequestMapping("/secKill")
@RestController
public class SeckillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 秒杀系统
     * @param secKillVo
     * @return
     */
    @RequestMapping(value = "/secKillTest",method = RequestMethod.POST)
    public Result secKillTest(@RequestBody SecKillVo secKillVo) throws Exception{
        //秒杀
        Boolean isSecKillSuccess = secKillService.secKillProduct(secKillVo.getKillId(), secKillVo.getSkuId(), secKillVo.getUserId(), secKillVo.getNum());
        if(isSecKillSuccess){
            return Result.ok().responseBody(SeckillResponse.SECKILL_SUCESS);
        }else{
            return Result.error().responseBody(SeckillResponse.SECKILL_FAIL);
        }
    }

}
