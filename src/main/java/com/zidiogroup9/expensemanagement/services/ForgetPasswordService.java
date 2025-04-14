package com.zidiogroup9.expensemanagement.services;

public interface ForgetPasswordService {
    String createForgetPasswordToken(String email);
    String generateEmailToken(String token);
}
