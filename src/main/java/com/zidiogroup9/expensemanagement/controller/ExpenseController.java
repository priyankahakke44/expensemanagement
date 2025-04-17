package com.zidiogroup9.expensemanagement.controller;


import com.zidiogroup9.expensemanagement.advices.ApiResponse;
import com.zidiogroup9.expensemanagement.dtos.ExpenseCategoryDto;
import com.zidiogroup9.expensemanagement.dtos.ExpenseDto;
import com.zidiogroup9.expensemanagement.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
@Secured("ROLE_EMPLOYEE")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final int PAGE_SIZE = 10;

    @PostMapping()
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
        return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseDto>> getAllExpense(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                PAGE_SIZE,
                Sort.by(sortBy)
        );
        return new ResponseEntity<>(expenseService.getAllExpense(pageable), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<ExpenseDto>> getExpenseByUser(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                PAGE_SIZE,
                Sort.by(sortBy)
        );
        return new ResponseEntity<>(expenseService.getExpenseByUser(pageable), HttpStatus.OK);
    }

}
