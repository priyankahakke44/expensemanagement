package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.*;
import com.zidiogroup9.expensemanagement.entities.User;

public interface AuthService {
    UserDto signUp(SignUpDto signUpDto);
    String[] login(LoginDto loginDto);
    String[] refreshToken(String refreshToken);
    UserDto getProfile();

}
