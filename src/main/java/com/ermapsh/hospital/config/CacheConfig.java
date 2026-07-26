package com.ermapsh.hospital.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){

        ObjectMapper objectMapper = new ObjectMapper();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.
                defaultCacheConfig().
                entryTtl(Duration.ofSeconds(60)).
                enableTimeToIdle().
                serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).
                serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJacksonJsonRedisSerializer(objectMapper)
                )).
                prefixCacheNameWith("test-my-redis-cache"); //

        return RedisCacheManager.
                builder(redisConnectionFactory).
                cacheDefaults(redisCacheConfiguration).
                build();
    }
}
