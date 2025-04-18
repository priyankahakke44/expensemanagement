package com.zidiogroup9.expensemanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.services.Impl.AuthServiceImpl;
import com.zidiogroup9.expensemanagement.services.Impl.UserServiceimpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserServiceimpl userServiceimpl;
	private final ModelMapper mapper;

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable(name = "id") String id, HttpServletResponse response) {
		UserDto userById = userServiceimpl.findUserById(id);

		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id,
			HttpServletResponse httpServletResponse) {
		UserDto deleteUser = userServiceimpl.deleteUser(id);
		if (deleteUser != null) {
			mapper.map(deleteUser, String.class);
			return new ResponseEntity<String>("User deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("somthing went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("update/{id}")
	private ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") String id,@RequestBody UpdateUserDto updateUserDto, HttpServletResponse response) {
		UserDto updatedUser = userServiceimpl.updateUser(id,updateUserDto);
		if (updatedUser != null) {
			return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(updatedUser, HttpStatus.NOT_FOUND);
		}
	}
}
