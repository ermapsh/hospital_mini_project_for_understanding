package com.ermapsh.hospital.service;

import com.ermapsh.hospital.TestContainerConfiguration;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.dto.SignupResponse;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.enums.Role;
import com.ermapsh.hospital.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@Slf4j
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest // works with repository
//@SpringBootTest // we also don't want whole application context so in that case we have to use Mockito Extension
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /*
     *  we have to follow this type of structure in Service related testing
     *   assign
     *
     *   act
     *
     *   assert
     * */

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private final Long id = 1L;
    private SignupRequest signupRequestDto;

    @BeforeEach
    void setUp() {
        mockUser = User.builder().
                id(id).
                name("mahesh").
                email("maheshmestri73@gmail.com").
                password("mahesh@123").
                roles(Set.of(Role.valueOf("USER"))).
                build();

        signupRequestDto = modelMapper.map(mockUser, SignupRequest.class);
    }

    @Test
    void testGetUserById_whenUserIdIsPresent_ThenReturnUserDto() {
        // assign

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser)); // stubbing

        // act
        SignupResponse signupResponse = userService.getUserById2(id);
        log.info(signupResponse.getEmail());

        // assert
        Assertions.assertThat(signupResponse.getId()).isEqualTo(id);
        Assertions.assertThat(signupResponse.getEmail()).isEqualTo(mockUser.getEmail());

        log.info(mockUser.getEmail());

        verify(userRepository).findById(id);
//        verify(userRepository).save(null); // will give error

//        verify(userRepository, atLeast(2)).findById(id); // will give error

        verify(userRepository, only()).findById(id); // only findById is getting called or not
    }


    @Test
        // happy test
    void testCreateNewUser_whenValidUser_thenCreateNewUser() {
        // assign
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty()); // here we are saying for there is query used findByEmail if we provide mock user then its will throw user already exist
        when(userRepository.save(any(User.class))).thenReturn(mockUser); // and on save method return that our mockuser

        // act
        SignupResponse signupResponse = userService.signUp(signupRequestDto);

        // assert
        Assertions.assertThat(signupResponse).isNotNull();
        Assertions.assertThat(signupResponse.getEmail()).isEqualTo(mockUser.getEmail());
        verify(userRepository).save(any(User.class));

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();
        Assertions.assertThat(capturedUser.getEmail()).isEqualTo(mockUser.getEmail());

    }

    @Test
        // sad test
    void testGetUserById_whenUserNotPresent_thenThrowException() {
//    arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act & assert
        Assertions.assertThatThrownBy(() -> userService.getUserById(1L)).
                isInstanceOf(UsernameNotFoundException.class).
                hasMessage("User with id 1 not found");
        verify(userRepository).findById(1L);
    }

    @Test
    void testCreateNewUser_WhenAttemptingCreateNewUserWithExitingEmail_thenTrowException() {
//        arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

//        act and assert
        Assertions.assertThatThrownBy(() -> userService.signUp(signupRequestDto)).
                isInstanceOf(BadCredentialsException.class).
                hasMessage("User email already exist " + mockUser.getEmail());
        verify(userRepository).findByEmail(mockUser.getEmail());
    }

    @Test
    void testFindByEmail_whenAttemptingToGetUserByEmail_thenThrowException() {
//        arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //        act and assert
        Assertions.assertThatThrownBy(() -> userService.loadUserByUsername(mockUser.getEmail())).
                isInstanceOf(UsernameNotFoundException.class).
                hasMessage("User not found with email: " + mockUser.getEmail());
        verify(userRepository).findByEmail(mockUser.getEmail());
    }

    @Test
    void testFindByEmail_whenAttemptingToGetUserByEmail_thenThrowNull() {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThat(userService.getUsrByEmail(mockUser.getEmail()))
                .isNull();
        verify(userRepository).findByEmail(mockUser.getEmail());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Assertions.assertThat(userService.save(mockUser)).isNotNull();

        verify(userRepository).save(mockUser);
    }
}