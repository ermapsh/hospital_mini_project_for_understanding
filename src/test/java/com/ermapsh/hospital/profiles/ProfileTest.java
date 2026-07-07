package com.ermapsh.hospital.profiles;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class ProfileTest {

    @Autowired
    private MethodDto methodDto;

    @Value("${my.env}")
    private String env;

    @Test
    void test() {
        System.out.println("my env ="+ env);
        System.out.println(methodDto.getData());
    }
}