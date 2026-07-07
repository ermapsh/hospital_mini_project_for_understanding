package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.TestContainerConfiguration;
import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.enums.Permission;
import com.ermapsh.hospital.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(TestContainerConfiguration.class)
public abstract class AbstractIntegrationIT {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected User user;
    protected SignupRequest testUser;
    protected LoginDto loginUser;

    @BeforeEach
    void init() {

        user = User.builder()
                .name("Mahesh Mestri")
                .email("maheshmestri12@gmail.com")
                .password(passwordEncoder.encode("Mahesh@12345"))
                .build();

        testUser = SignupRequest.builder()
                .name("Mahesh Mestri")
                .email("maheshmestri12@gmail.com")
                .password("Mahesh@12345")
                .roles(Set.of(Role.USER))
                .permissions(Set.of(Permission.POST_VIEW, Permission.USER_VIEW))
                .build();

        loginUser = LoginDto.builder()
                .email("maheshmestri12@gmail.com")
                .password("Mahesh@12345")
                .build();
    }
}