package com.zidiogroup9.expensemanagement.services.Impl;

import java.util.Optional;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.exceptions.InvalidPasswordException;
import com.zidiogroup9.expensemanagement.exceptions.PasswordMismatchException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zidiogroup9.expensemanagement.dtos.UpdateUserDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.exceptions.ResourceNotFoundException;
import com.zidiogroup9.expensemanagement.exceptions.RuntimeConflictException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	@Override
	public UserDto getProfile() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return modelMapper.map(user,UserDto.class);
	}

	@Override
	public void changePassword(ChangePasswordDto changePasswordDto) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
			throw new InvalidPasswordException("Wrong Password");
		}
		if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())){
			throw new PasswordMismatchException("Password are not same");
		}
		user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	public UserDto findUserById(String id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + id));
		return modelMapper.map(user, UserDto.class);

	}

	@Override
	public UserDto deleteUser(String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new RuntimeConflictException("User Not Found Withi id" + id);
		} else {

			userRepository.deleteById(id);
			return modelMapper.map(user, UserDto.class);
		}

	}

	@Override
	public UserDto updateUser(String id,UpdateUserDto updateUserDto) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty()) {
			throw new RuntimeConflictException("somthing went wrong");

		} else {
			User userToBeUpdate = modelMapper.map(userOptional, User.class);

			userToBeUpdate.setName(updateUserDto.getName());
			userToBeUpdate.setEmail(updateUserDto.getEmail());
			userToBeUpdate.setProfile(updateUserDto.getProfile());
			userToBeUpdate.setRoles(updateUserDto.getRoles());
			userToBeUpdate.setDepartment(updateUserDto.getDepartment());
			User saveduser = userRepository.save(userToBeUpdate);
			return modelMapper.map(saveduser, UserDto.class);

		}
	}

}
