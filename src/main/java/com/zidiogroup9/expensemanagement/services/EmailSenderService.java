package com.zidiogroup9.expensemanagement.services;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
