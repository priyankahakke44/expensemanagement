package com.zidiogroup9.expensemanagement.services;

import com.zidiogroup9.expensemanagement.dtos.ExpenseCategoryDto;
import com.zidiogroup9.expensemanagement.entities.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryService {
    ExpenseCategoryDto createExpenseCategory(ExpenseCategoryDto expenseCategoryDto);
    void deleteExpenseCategoryById(String id);
    List<ExpenseCategoryDto> getAllExpenseCategory();
    ExpenseCategory gerExpenseCategoryById(String id);
}
