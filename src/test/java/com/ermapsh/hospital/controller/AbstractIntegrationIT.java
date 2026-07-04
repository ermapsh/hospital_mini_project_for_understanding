package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.TestContainerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest
@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationIT {

    @Autowired
    WebTestClient webTestClient;
}
