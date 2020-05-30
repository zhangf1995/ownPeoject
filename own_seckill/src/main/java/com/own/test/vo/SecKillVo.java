package com.own.test.vo;

import lombok.Data;

/*
 * @author  zf
 * @date  2020/5/24 11:00 上午
 */
@Data
public class SecKillVo {
    private String killId;
    //后期放入session中
    private String userId;
    private String skuId;
    private Integer num;
}
