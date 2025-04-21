package com.zidiogroup9.expensemanagement.controller;


import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.*;
import com.zidiogroup9.expensemanagement.services.Impl.UserServiceImpl;
import com.zidiogroup9.expensemanagement.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceimpl;
    private final ModelMapper mapper;

    private final UserService userService;

    @GetMapping(path = "/profile")
    public ResponseEntity<UserDto> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }
    @PatchMapping(path = "/changepassword")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
        ApiResponse<String> response = new ApiResponse<>("Password changed successfully");
        return ResponseEntity.ok(response);
    }

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
    @PatchMapping(path = "/updateProfile")
    public ResponseEntity<UserDto> updateProfile(UpdateProfileDto updateProfileDto){
        Map<String, Object> updates = new HashMap<>();
        if (updateProfileDto.getName() != null) {
            updates.put("name", updateProfileDto.getName());
        }
        if (updateProfileDto.getImage() != null && !updateProfileDto.getImage().isEmpty()) {
            updates.put("profile", updateProfileDto.getImage());
        }
        return new ResponseEntity<>(userService.updateProfile(updates),HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/onboardManager")
    public ResponseEntity<UserDto> onboardNewManager(@RequestBody OnboardingDto onboardingDto){
        return new ResponseEntity<>(userService.onboardManager(onboardingDto.getEmail()),HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/onboardFinance")
    public ResponseEntity<UserDto> onboardNewFinance(@RequestBody OnboardingDto onboardingDto){
        return new ResponseEntity<>(userService.onboardFinance(onboardingDto.getEmail()),HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/assignDepartment")
    public ResponseEntity<UserDto> assignDepartment(@RequestBody AssignDepartmentDto assignDepartmentDto){
        return new ResponseEntity<>(userService.assignDepartment(assignDepartmentDto.getEmail(),assignDepartmentDto.getDepartmentDto()),HttpStatus.CREATED);
    }
}
