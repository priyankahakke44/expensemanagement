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
        ExpenseCategory expenseCategory = modelMapper.map(expenseCategoryDto,ExpenseCategory.class);
        ExpenseCategory toSavedExpenseCategory = expenseCategoryRepository.save(expenseCategory);
        return modelMapper.map(toSavedExpenseCategory,ExpenseCategoryDto.class);
    }

    @Override
    public void deleteExpenseCategoryById(String id) {
        expenseCategoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Expense Category not found with id :"+id)
        );
        expenseCategoryRepository.deleteById(id);
    }

    @Override
    public List<ExpenseCategoryDto> getAllExpenseCategory() {
        try {
            return expenseCategoryRepository.findAll()
                    .stream()
                    .map(expenseCategory -> modelMapper.map(expenseCategory,ExpenseCategoryDto.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch expense category", e);
        }
    }
}
