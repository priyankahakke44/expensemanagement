package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.exceptions.InvalidPasswordException;
import com.zidiogroup9.expensemanagement.exceptions.PasswordMismatchException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.services.FileUploaderService;
import com.zidiogroup9.expensemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceIml implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileUploaderService fileUploaderService;

    @Override
    public UserDto getProfile() {
        User user = getCurrentUser();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong Password");
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Password are not same");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto updateProfile(Map<String, Object> updates) {
        User user = getCurrentUser();
        updates.forEach((field, value) -> {
            try {
                if ("profile".equals(field)) {
                    Field fieldToBeUpdated = ReflectionUtils.findRequiredField(User.class, "profile");
                    MultipartFile profileImage = (MultipartFile) value;
                    String profileImageUrl = fileUploaderService.uploadFile(profileImage, "users",user.getEmail());
                    fieldToBeUpdated.setAccessible(true);
                    ReflectionUtils.setField(fieldToBeUpdated, user, profileImageUrl);
                } else {
                    Field fieldToBeUpdated = ReflectionUtils.findRequiredField(User.class, field);
                    fieldToBeUpdated.setAccessible(true);
                    ReflectionUtils.setField(fieldToBeUpdated, user, value);
                }
            } catch (Exception e){
                log.info("Failed to update field: " + field);
                throw new RuntimeException("Failed to update field: " + field, e);
            }
        });
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
