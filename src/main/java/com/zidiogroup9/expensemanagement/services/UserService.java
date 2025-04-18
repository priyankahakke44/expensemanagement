package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;

public interface UserService {
    UserDto getProfile();
    void changePassword(ChangePasswordDto changePasswordDto);
}
package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;

public interface UserService {
    UserDto getProfile();
    void changePassword(ChangePasswordDto changePasswordDto);
}
package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;

public interface UserService {

	UserDto findUserById(String id);

	UserDto deleteUser(String id);

	UserDto updateUser(String id,UpdateUserDto updateUserDto);
}
