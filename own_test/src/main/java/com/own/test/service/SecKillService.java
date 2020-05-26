package com.own.test.service;

/*
 * @author  zf
 * @date  2020/5/23 3:04 下午
 */
public interface SecKillService {
    Boolean secKillProduct(String killId, String skuId, String userId, Integer num) throws Exception;
}
