package com.own.test.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*
 * @author  zf
 * @date  2020/5/23 9:33 下午
 */
@Data
public class SecKillTo {
    private String killId;
    private String skuId;
    private BigDecimal price;
    private Date seckillStartTime;
    private Date seckillEndTime;
}
