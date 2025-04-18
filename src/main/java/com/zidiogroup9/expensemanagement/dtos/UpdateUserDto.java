package com.zidiogroup9.expensemanagement.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import com.zidiogroup9.expensemanagement.entities.Department;
import com.zidiogroup9.expensemanagement.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
	 private String name;
	    private String email;
	    private Set<Role> roles;
	    private String profile;
	    private Department department;
	    private LocalDateTime updatedAt;
}
