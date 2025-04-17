package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.ExpenseCategoryDto;
import com.zidiogroup9.expensemanagement.entities.ExpenseCategory;
import com.zidiogroup9.expensemanagement.exceptions.ResourceNotFoundException;
import com.zidiogroup9.expensemanagement.repositories.ExpenseCategoryRepository;
import com.zidiogroup9.expensemanagement.services.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
    private final ModelMapper modelMapper;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public ExpenseCategoryDto createExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {
        try {
            ExpenseCategory expenseCategory = modelMapper.map(expenseCategoryDto, ExpenseCategory.class);
            return modelMapper.map(expenseCategoryRepository.save(expenseCategory), ExpenseCategoryDto.class);
        } catch (Exception e){
            throw new RuntimeException("Failed to create expense category ",e);
        }
    }

    @Override
    public void deleteExpenseCategoryById(String id) {
        gerExpenseCategoryById(id);
        expenseCategoryRepository.deleteById(id);
    }

    @Override
    public List<ExpenseCategoryDto> getAllExpenseCategory() {
        try {
            return expenseCategoryRepository.findAll()
                    .stream()
                    .map(expenseCategory -> modelMapper.map(expenseCategory, ExpenseCategoryDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch expense category", e);
        }
    }

    @Override
    public ExpenseCategory gerExpenseCategoryById(String id) {
        return expenseCategoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Expense Category not found with id :" + id)
        );
    }

}
