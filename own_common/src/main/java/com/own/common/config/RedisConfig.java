package com.own.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/*
 * @author  zf
 * @date  2020/5/23 6:37 下午
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate newRedisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setDefaultSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
