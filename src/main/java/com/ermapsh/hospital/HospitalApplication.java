package com.ermapsh.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {

//    @Autowired
//    private StringRedisTemplate redisTemplate;;

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(cacheManager.getClass().getName());
//        redisTemplate.opsForValue().set("hello", "world");
//        System.out.println(redisTemplate.opsForValue().get("hello"));
    }
}
