package com.ermapsh.hospital.repository;

import com.ermapsh.hospital.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest // whole application context having
@DataJpaTest // data repo scans and having context and also uses by default in memory database & @Transactional so we don't need configure by manually
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // replace with h2database cause its best practice to use in testing, cause we dont wanted to populate the real db
class UserRepositoryTest {

    /*
       Test should be written like this
    *       Arrange (Given)
    *
    *       Act (When)
    *
    *      Assert (Then)
    */

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Mahesh Mestri")
                .email("maheshmestri73@gmail.com")
                .password("Mahesh@12345")
                .build();
    }


    @Test
    void findByEmail_whenEmailIsValid_thenReturnUser() {
//        Arrange (Given)
        userRepository.save(user);

//        Act (When)
        Optional<User> NewUser = userRepository.findByEmail(user.getEmail());

//        Assert (Then)
        Assertions.assertThat(NewUser).isNotNull();
        Assertions.assertThat(NewUser).isNotEmpty();
        Assertions.assertThat(NewUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findByEmail_whenEmailIsNotFound_thenReturnNull() {
//        given

        String email = "xyx@gmail.com";

//        when
        Optional<User> user1 = userRepository.findByEmail(email);

//        then
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1).isNotEmpty();
        Assertions.assertThat(user1.get().getEmail()).isEqualTo(email);
    }
}