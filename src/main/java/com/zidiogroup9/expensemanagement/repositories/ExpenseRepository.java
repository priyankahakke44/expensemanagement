package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.entities.Expense;
import com.zidiogroup9.expensemanagement.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,String> {
    Page<Expense> findByUser(User user, Pageable pageable);
}
