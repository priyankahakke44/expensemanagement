package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.*;

public interface AuthService {
    UserDto signUp(SignUpDto signUpDto);
    String[] login(LoginDto loginDto);
    String[] refreshToken(String refreshToken);
    void sendResetLink(String email);
    void resetPassword(String token, String newPassword);
}
