package com.zidiogroup9.expensemanagement.controller;


import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.ChangePasswordDto;
import com.zidiogroup9.expensemanagement.dtos.UpdateProfileDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.services.AuthService;
import com.zidiogroup9.expensemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(response);
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
        return ResponseEntity.ok(userService.updateProfile(updates));
    }
}
