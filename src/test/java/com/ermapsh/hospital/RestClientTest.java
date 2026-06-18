package com.ermapsh.hospital;

import com.ermapsh.hospital.controller.RestClientUsageController;
import com.ermapsh.hospital.dto.RestDataDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
@RequiredArgsConstructor
public class RestClientTest {



    @Test
    @Order(2)
    public void get(){
        System.out.println("fist task but second priority");
    }

    @Test
    @Order(1)
    public void getAll(){
        System.out.println("second task but priority");
    }


}
