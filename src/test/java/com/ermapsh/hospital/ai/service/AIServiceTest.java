package com.ermapsh.hospital.ai.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AIServiceTest {

    @Autowired
    private AIService aiService;

    @Test
    public void getJoke(){
        System.out.println("Calling ai");
        System.out.println(aiService.getJoke("On me"));
    }
}