package com.zidiogroup9.expensemanagement.controller;

import com.zidiogroup9.expensemanagement.dtos.DepartmentDto;
import com.zidiogroup9.expensemanagement.dtos.SignUpDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class DepartmentController {
    private final DepartmentService departmentService;


    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable String departmentId){
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }
}
