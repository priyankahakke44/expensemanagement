package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateProfileDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;

import java.util.Map;

public interface UserService {
    UserDto getProfile();
    User getCurrentUser();
    void changePassword(ChangePasswordDto changePasswordDto);
    UserDto updateProfile(Map<String, Object> updates);
    User loadUserByEmail(String email);
     User save(User newUser);
}
