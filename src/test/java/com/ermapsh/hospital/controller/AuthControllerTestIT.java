package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.LoginResponse;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.enums.Permission;
import com.ermapsh.hospital.enums.Role;
import com.ermapsh.hospital.repository.SessionRepository;
import com.ermapsh.hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AuthControllerTestIT extends AbstractIntegrationIT{

    @Spy
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void testSignupUser() {
        user.setEmail("maheshmestri73@gmail.com");
        User savedUser = userRepository.save(user);
//        EntityExchangeResult<SignupResponse> result = webTestClient.post()

        webTestClient.post().
                uri("/auth/signup")
                .bodyValue(testUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.email").isEqualTo(testUser.getEmail());

//                .expectBody(SignupResponse.class)
//                .returnResult();

//        SignupResponse response = result.getResponseBody();
//
//        assertThat(response).isNotNull();
//        assertThat(response.getName()).isEqualTo(testUser.getName());
//        assertThat(response.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    void testLoginUser_success() {
        User savedUser = userRepository.save(user);
        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(loginUser)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LoginResponse.class)
                .value(response -> {
                    assertThat(response.accessToken()).isNotNull();
                    assertThat(response.refreshToken()).isNotNull();
                });
    }

    @Test
    void testCreateNewUser_whenEmailAlreadyExist_throwException() {
        User savedUser = userRepository.save(user);
        webTestClient.post()
                .uri("/auth/signup")
                .bodyValue(testUser)
                .exchange()
                .expectStatus().is4xxClientError();

    }
}