package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ExpenseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {
    ExpenseDto createExpense(ExpenseDto expenseDto);
    List<ExpenseDto> getAllExpense(Pageable pageable);
    List<ExpenseDto> getExpenseByUser(Pageable pageable);
}
