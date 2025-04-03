package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.TestContainerConfiguration;
import com.zidiogroup9.expensemanagement.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    @Autowired
    private MySQLContainer<?> mySQLContainer;

    @Test
    void testDatabaseConnection() {
        assertThat(mySQLContainer.isRunning()).isTrue();
    }
    @BeforeEach
    void setup(){
        user = User.builder()
                .name("example name")
                .email("example@gmail.com")
                .password("Example@123")
                .build();
    }
    @Test
    void testFindByEmail_whenEmailIsValid_thanReturnUser() {
        userRepository.save(user);
        Optional<User> presentUser= userRepository.findByEmail(user.getEmail());
        assertThat(presentUser).isNotNull();
        assertThat(presentUser).isNotEmpty();
        assertThat(presentUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thanReturnUserEmptyList() {
        String email = "example1@gmail.com";
        Optional<User> user1 = userRepository.findByEmail(email);
        assertThat(user1).isNotPresent();
        assertThat(user1).isEmpty();
    }
}