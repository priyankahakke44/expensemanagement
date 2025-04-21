package com.zidiogroup9.expensemanagement.services.Impl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.DepartmentDto;
import com.zidiogroup9.expensemanagement.entities.Department;
import com.zidiogroup9.expensemanagement.entities.enums.Role;
import com.zidiogroup9.expensemanagement.exceptions.InvalidPasswordException;
import com.zidiogroup9.expensemanagement.exceptions.PasswordMismatchException;
import com.zidiogroup9.expensemanagement.services.DepartmentService;
import com.zidiogroup9.expensemanagement.services.FileUploaderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final FileUploaderService fileUploaderService;
	private final DepartmentService departmentService;

	@Override
	public UserDto getProfile() {
		User user = getCurrentUser();
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public void changePassword(ChangePasswordDto changePasswordDto) {
		User user = getCurrentUser();
		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
			throw new InvalidPasswordException("Wrong Password");
		}
		if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
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

	@Override
	public UserDto updateProfile(Map<String, Object> updates) {
		User user = getCurrentUser();
		updates.forEach((field, value) -> {
			try {
				if ("profile".equals(field)) {
					Field fieldToBeUpdated = ReflectionUtils.findRequiredField(User.class, "profile");
					MultipartFile profileImage = (MultipartFile) value;
					String profileImageUrl = fileUploaderService.uploadFile(profileImage, "users",user.getEmail());
					fieldToBeUpdated.setAccessible(true);
					ReflectionUtils.setField(fieldToBeUpdated, user, profileImageUrl);
				} else {
					Field fieldToBeUpdated = ReflectionUtils.findRequiredField(User.class, field);
					fieldToBeUpdated.setAccessible(true);
					ReflectionUtils.setField(fieldToBeUpdated, user, value);
				}
			} catch (Exception e){
				log.info("Failed to update field: " + field);
				throw new RuntimeException("Failed to update field: " + field, e);
			}
		});
		return modelMapper.map(userRepository.save(user),UserDto.class);
	}

	@Override
	public User loadUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public User save(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public UserDto assignDepartment(String email, DepartmentDto departmentDto) {
		User user = getUserByEmail(email);
		if (!departmentService.checkDepartmentById(departmentDto.getId())){
			throw new ResourceNotFoundException("Department not found");
		}
		user.setDepartment(modelMapper.map(departmentDto, Department.class));
		return modelMapper.map(userRepository.save(user),UserDto.class);
	}

	@Override
	public UserDto onboardManager(String email) {
		User user = getUserByEmail(email);
		if (user.getRoles().contains(Role.MANAGER)){
			throw new RuntimeConflictException("User with email" + email + "is already a Manager");
		}
		user.getRoles().add(Role.MANAGER);
		return modelMapper.map(userRepository.save(user),UserDto.class);
	}

	@Override
	public UserDto onboardFinance(String email) {
		User user = getUserByEmail(email);
		if (user.getRoles().contains(Role.FINANCE)){
			throw new RuntimeConflictException("User with email" + email + "is already a Manager");
		}
		user.getRoles().add(Role.FINANCE);
		return modelMapper.map(userRepository.save(user),UserDto.class);
	}

	@Override
	public User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private User getUserByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(
				()->new ResourceNotFoundException("User not found with email: "+email)
		);
	}

}
