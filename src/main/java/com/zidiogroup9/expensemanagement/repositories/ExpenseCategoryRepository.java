package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory,String> {
}
