package com.own.common.config;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/*
 * @author  zf
 * @date  2020/5/23 10:14 下午
 */
public class RedissonConfig {

    @Autowired
    private Environment env;

    /**
     * RedissonClient,单机模式,简单配置，未做判断
     * */
    @Bean
    public RedissonClient redisson() {
        Integer database = Integer.valueOf(env.getProperty("spring.redis.database")) ;
        String host = env.getProperty("spring.redis.host");
        String port = env.getProperty("spring.redis.port");

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        singleServerConfig.setDatabase(database);
        return Redisson.create(config);
    }
}
