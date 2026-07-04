package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.TestContainerConfiguration;
import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.LoginResponse;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.dto.SignupResponse;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainerConfiguration.class)
class AuthControllerTestIT {

    @Spy
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private User user;
    private SignupRequest testUser;
    private LoginDto loginUser;

    @BeforeEach
    void setup() {
        user = User.builder()
                .name("Mahesh Mestri")
                .email("maheshmestri12@gmail.com")
                .password(passwordEncoder.encode("Mahesh@12345"))
                .build();

        testUser = SignupRequest.builder()
                .name("Mahesh Mestri")
                .email("maheshmestri12@gmail.com")
                .password(passwordEncoder.encode("Mahesh@12345"))
                .roles(Set.of(Role.valueOf("USER")))
                .permissions(Set.of(Permission.valueOf("POST_VIEW"), Permission.valueOf("USER_VIEW")))
                .build();

        loginUser = LoginDto.builder().
                email("maheshmestri12@gmail.com").
                password("Mahesh@12345").
                build();
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