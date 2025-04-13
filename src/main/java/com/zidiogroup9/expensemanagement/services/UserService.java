package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;

public interface UserService {
    UserDto getProfile();
    void changePassword(ChangePasswordDto changePasswordDto);
}
