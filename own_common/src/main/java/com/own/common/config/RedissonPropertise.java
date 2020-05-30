package com.own.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * @author  zf
 * @date  2020/5/23 10:15 下午
 * 只是为了验证下自定义启动器(可以忽略-。-)
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonPropertise {

    /**
     * spring.redis.database=
     * spring.redis.host=
     * spring.redis.port=
     * spring.redis.password=
     * spring.redis.ssl=
     * spring.redis.timeout=
     * spring.redis.cluster.nodes=
     * spring.redis.sentinel.master=
     * spring.redis.sentinel.nodes=
     */
    private int database = 0;
    private String host = "localhost";
    private String password;
    private int port = 6379;
    //剩下需要的配置可以自己配置

}
