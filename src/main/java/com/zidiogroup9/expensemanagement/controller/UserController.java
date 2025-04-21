package com.zidiogroup9.expensemanagement.controller;

import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;


import com.zidiogroup9.expensemanagement.services.Impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserServiceImpl userServiceImpl;
	private final ModelMapper mapper;

	@GetMapping(path = "/profile")
	public ResponseEntity<UserDto> getProfile() {
		return ResponseEntity.ok(userServiceImpl.getProfile());
	}

	@PatchMapping(path = "/changePassword")
	public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		userServiceImpl.changePassword(changePasswordDto);
		ApiResponse<String> response = new ApiResponse<>("Password changed successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable(name = "id") String id, HttpServletResponse response) {
		UserDto userById = userServiceImpl.findUserById(id);

		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id,
			HttpServletResponse httpServletResponse) {
		UserDto deleteUser = userServiceImpl.deleteUser(id);
		if (deleteUser != null) {
			mapper.map(deleteUser, String.class);
			return new ResponseEntity<String>("User deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("somthing went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("update/{id}")
	private ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") String id,
			@RequestBody UpdateUserDto updateUserDto, HttpServletResponse response) {
		UserDto updatedUser = userServiceImpl.updateUser(id, updateUserDto);
		if (updatedUser != null) {
			return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(updatedUser, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("all/{pageNo}/{noOfRecords}")
	public ResponseEntity<List<UserDto>> findAllUsers(@PathVariable(name = "pageNo") int pageNo,
			@PathVariable(name = "noOfRecords") int noOfRecords, HttpServletResponse response) {
		
		List<UserDto> findall = userServiceImpl.findall(pageNo,noOfRecords);
		return new ResponseEntity<List<UserDto>>(findall, HttpStatus.FOUND);
	}

}
