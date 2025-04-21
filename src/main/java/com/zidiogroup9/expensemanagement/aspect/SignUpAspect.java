package com.zidiogroup9.expensemanagement.aspect;

import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SignUpAspect {
    @Autowired
    private EmailSenderService emailSenderService;

    @AfterReturning(pointcut = "execution(* com.zidiogroup9.expensemanagement.services.Impl.AuthServiceImpl.signUp(..))",
            returning = "userDto")
    public void sendEmailAfterRegistration(Object userDto) {
        log.info("called after signup");
        if (userDto != null) {
            UserDto dto = (UserDto) userDto;
            String email = dto.getEmail();
            String name = dto.getName();
            String subject = "Thank You for Registering with Enterprise Expense Management System!";
            String body = "Hi " + name + ",\n" +
                    "\n" +
                    "Thank you for registering with Enterprise Expense Management System! Your account has been successfully created.\n" +
                    "\n" +
                    "Welcome \n" +
                    "The Enterprise Expense Management System Team\n";
            emailSenderService.sendEmail(email, subject, body);
        }
    }
}
