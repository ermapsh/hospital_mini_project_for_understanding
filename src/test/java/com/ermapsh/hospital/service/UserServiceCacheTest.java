package com.ermapsh.hospital.service;

import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceCacheTest {

    /*
    * in test cache begin empty if execuation
    * */

    @Autowired
    private UserService userService;

    @Test
    @Order(0)
    public void createUser(){
        userService.save(User.builder().
                name("mahesh").
                email("maheshmestri73@gmail.com").
                password("mahesh@123").
                roles(Set.of(Role.valueOf("USER"))).
                build());
    }

    @Test
    @Order(1)
    public void getUsrByEmail(){
        log.info("first time calling: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());

        log.info("second time calling - from cache: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());
    }


    @Test
    @Order(2)
    public void updateByEmail(){
        userService.updateUser("maheshmestri73@gmail.com");
    }

    @Test
    @Order(3)
    public void getUsrByEmail2(){

        log.info("first time calling: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());

        log.info("second time calling - from cache: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());
    }

    @Test
    @Order(4)
    public void removeCache(){
        log.info("before cache removing - from db: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());
        userService.deleteUser("maheshmestri73@gmail.com");
        log.info("after cache removing - from db: {}", userService.getUsrByEmail("maheshmestri73@gmail.com").getName());
    }
}