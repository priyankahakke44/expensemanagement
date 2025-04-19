package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    List<DepartmentDto> getAllDepartment();
    DepartmentDto getDepartmentById(String id);
}
