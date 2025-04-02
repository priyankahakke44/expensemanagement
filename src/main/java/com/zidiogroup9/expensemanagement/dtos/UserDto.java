package com.zidiogroup9.expensemanagement.dtos;

import com.zidiogroup9.expensemanagement.entities.Department;
import com.zidiogroup9.expensemanagement.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private String email;
    private Set<Role> roles;
    private String profile;
    private Department department;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
