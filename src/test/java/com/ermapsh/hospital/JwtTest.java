package com.ermapsh.hospital;

import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtService jwtService;

    @Test
    public void createJwtToken(){
//        User user = new User(4L, "Mahesh", "mahesh@gmail.com");
//        String token = jwtService.generateToken(user);
//        System.out.println(token);
    }

    @Test
    public void getIdFromJwtToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJNYWhlc2giLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNzgyMjE2ODQxLCJleHAiOjE3ODIyMTY5MDF9.iwZ2pQXANlCV4P_QSXSQIMGdYfiVilWuSJwIuu_RWy77yX6X3rCGSLTdCyXsYfHtXjNzNNnVydmrk2nuIaSDoQ";
        Long id = jwtService.getUserIdFromJwtToken(token);
        System.out.println(id);
    }
}
