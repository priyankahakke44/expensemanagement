package com.zidiogroup9.expensemanagement.controller;

import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.ExpenseCategoryDto;
import com.zidiogroup9.expensemanagement.dtos.SignUpDto;
import com.zidiogroup9.expensemanagement.dtos.UserDto;
import com.zidiogroup9.expensemanagement.services.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenseCategory")
@Secured("ROLE_ADMIN")
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping()
    public ResponseEntity<ExpenseCategoryDto> createExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto){
        return new ResponseEntity<>(expenseCategoryService.createExpenseCategory(expenseCategoryDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseCategoryDto>> getAllExpenseCategory(){
        return new ResponseEntity<>(expenseCategoryService.getAllExpenseCategory(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{expenseCategoryId}")
    public ResponseEntity<ApiResponse<?>> deleteExpenseCategoryById(@PathVariable String expenseCategoryId){
        ApiResponse<String> response = new ApiResponse<>("Expense category deleted successfully");
        return ResponseEntity.ok(response);
    }
}
