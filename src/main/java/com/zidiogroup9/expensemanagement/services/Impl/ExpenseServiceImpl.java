package com.zidiogroup9.expensemanagement.services.Impl;


import com.zidiogroup9.expensemanagement.dtos.ExpenseDto;
import com.zidiogroup9.expensemanagement.entities.Expense;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.entities.enums.ApproverRole;
import com.zidiogroup9.expensemanagement.entities.enums.Status;
import com.zidiogroup9.expensemanagement.exceptions.DepartmentNotAssignedException;
import com.zidiogroup9.expensemanagement.repositories.ExpenseRepository;
import com.zidiogroup9.expensemanagement.services.ExpenseService;
import com.zidiogroup9.expensemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        User user = userService.getCurrentUser();
        if (user.getDepartment() == null){
            throw new DepartmentNotAssignedException("Department not assigned");
        }
        Expense expense = modelMapper.map(expenseDto, Expense.class);
        expense.setInvoice(null);  // TODO : implement firebase to store image and pdf documents
        expense.setUser(user);
        expense.setApprovedBy(ApproverRole.NONE);
        expense.setStatus(Status.PENDING);
        return modelMapper.map(expenseRepository.save(expense), ExpenseDto.class);
    }

    @Override
    public List<ExpenseDto> getAllExpense(Pageable pageable) {
        try {
            return expenseRepository.findAll(pageable)
                    .stream()
                    .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch expense ", e);
        }
    }

    @Override
    public List<ExpenseDto> getExpenseByUser(Pageable pageable) {
        try {
            User user = userService.getCurrentUser();
            return expenseRepository.findByUser(user, pageable)
                    .stream()
                    .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch expense ", e);
        }
    }

}
