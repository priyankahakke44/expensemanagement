package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.DepartmentDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;

import java.util.Map;

public interface UserService {

	UserDto findUserById(String id);

	UserDto deleteUser(String id);

	UserDto updateUser(String id,UpdateUserDto updateUserDto);

    UserDto getProfile();
    User getCurrentUser();
    void changePassword(ChangePasswordDto changePasswordDto);
    UserDto updateProfile(Map<String, Object> updates);
    User loadUserByEmail(String email);
    User save(User newUser);
    UserDto assignDepartment(String email, DepartmentDto departmentDto);
    UserDto onboardManager(String email);
    UserDto onboardFinance(String email);
}
