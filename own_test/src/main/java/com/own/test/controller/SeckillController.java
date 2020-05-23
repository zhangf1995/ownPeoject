package com.own.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author  zf
 * @date  2020/5/19 10:24 下午
 */
@RequestMapping("/miaosha")
@RestController
public class SeckillController {

    @RequestMapping(value = "/miaoshaTest",method = RequestMethod.POST)
    public void miaoshaTest(){
        //秒杀
    }
}
