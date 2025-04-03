package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.TestContainerConfiguration;
import com.zidiogroup9.expensemanagement.dtos.SignUpDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.exceptions.RuntimeConflictException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.services.Impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private User mockUser;
    private SignUpDto mockSignUpDto;
    @BeforeEach
    void setup(){
        mockUser = User.builder()
                .id("3e40d468-c107-4a3f-8c61-749e01737c9b")
                .name("example name")
                .email("example@gmail.com")
                .password("Example@123")
                .build();
        mockSignUpDto = modelMapper.map(mockUser, SignUpDto.class);
    }

    @Test
    void testSignUp_whenValidUserThanSignUp(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        UserDto userDto = authService.signUp(mockSignUpDto);
        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(mockSignUpDto.getEmail());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();
        assertThat(captureUser.getEmail()).isEqualTo(mockUser.getEmail());
    }
    @Test
    void testSignUp_whenAttemptingToSignUpWithExitingEmail_throwException(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        assertThatThrownBy(()->authService.signUp(mockSignUpDto))
                .isInstanceOf(RuntimeConflictException.class)
                .hasMessage("Cannot signup, User already exists with email "+mockUser.getEmail());

        verify(userRepository).findByEmail(mockSignUpDto.getEmail());
        verify(userRepository,never()).save(any());
    }
}