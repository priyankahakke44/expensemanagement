package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.DepartmentDto;
import com.zidiogroup9.expensemanagement.entities.Department;
import com.zidiogroup9.expensemanagement.exceptions.ResourceNotFoundException;
import com.zidiogroup9.expensemanagement.exceptions.RuntimeConflictException;
import com.zidiogroup9.expensemanagement.repositories.DepartmentRepository;
import com.zidiogroup9.expensemanagement.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        List<Department> departments = departmentRepository.findByName(departmentDto.getName());
        if (!departments.isEmpty()) {
            throw new RuntimeConflictException("Department already exists with name: " +departmentDto.getName());
        }
        Department department = modelMapper.map(departmentDto, Department.class);
        return modelMapper.map(departmentRepository.save(department),DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        try {
            return departmentRepository.findAll()
                    .stream()
                    .map(department -> modelMapper.map(department, DepartmentDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch departments", e);
        }
    }

    @Override
    public DepartmentDto getDepartmentById(String id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department not found id " + id)
        );
        return modelMapper.map(department, DepartmentDto.class);
    }
}
