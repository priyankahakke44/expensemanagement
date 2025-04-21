package com.zidiogroup9.expensemanagement.services;

import java.util.List;



import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;


public interface UserService {

	UserDto findUserById(String id);

	UserDto deleteUser(String id);

	UserDto updateUser(String id, UpdateUserDto updateUserDto);

	UserDto getProfile();

	void changePassword(ChangePasswordDto changePasswordDto);

	

	public List<UserDto> findall(int pageNo, int noOfRecords);
}
