package com.zidiogroup9.expensemanagement.controller;


import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.*;
import com.zidiogroup9.expensemanagement.services.AuthService;
import com.zidiogroup9.expensemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping(path = "/profile")
    public ResponseEntity<UserDto> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }
    @PatchMapping(path = "/changePassword")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
        ApiResponse<String> response = new ApiResponse<>("Password changed successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
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
