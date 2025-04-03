package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.*;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.entities.enums.Role;
import com.zidiogroup9.expensemanagement.exceptions.RuntimeConflictException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()){
            throw new RuntimeConflictException("Cannot signup, User already exists with email " + signUpDto.getEmail());
        }
        User mappedUser = modelMapper.map(signUpDto,User.class);
        mappedUser.setProfile(null);
        mappedUser.setRoles(Set.of(Role.EMPLOYEE));
        mappedUser.setDepartment(null);
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User saveduser = userRepository.save(mappedUser);
        return modelMapper.map(saveduser,UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        return null;
    }
}
