package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.exceptions.InvalidPasswordException;
import com.zidiogroup9.expensemanagement.exceptions.PasswordMismatchException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public UserDto getProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
            throw new InvalidPasswordException("Wrong Password");
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())){
            throw new PasswordMismatchException("Password are not same");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }
}
