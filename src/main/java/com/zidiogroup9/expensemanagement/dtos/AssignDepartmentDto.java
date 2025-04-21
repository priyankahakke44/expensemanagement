package com.zidiogroup9.expensemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignDepartmentDto {
    private String email;
    private DepartmentDto departmentDto;
}
