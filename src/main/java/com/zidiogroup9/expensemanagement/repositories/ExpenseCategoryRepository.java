package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory,String> {
}
