package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateProfileDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;

import java.util.Map;

public interface UserService {
    UserDto getProfile();
    void changePassword(ChangePasswordDto changePasswordDto);
    UserDto updateProfile(Map<String, Object> updates);
}
