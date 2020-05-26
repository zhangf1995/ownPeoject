package com.own.common.config;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @author  zf
 * @date  2020/5/23 10:14 下午
 */
@Configuration
//test custom
//@EnableConfigurationProperties(RedissonPropertise.class)
public class RedissonConfig {
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    /**
     * RedissonClient,单机模式
     * */
    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        singleServerConfig.setDatabase(database);
        return Redisson.create(config);
    }
}
