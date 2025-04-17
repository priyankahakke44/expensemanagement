package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;

public interface UserService {
    UserDto getProfile();
    User getCurrentUser();
    void changePassword(ChangePasswordDto changePasswordDto);
}
