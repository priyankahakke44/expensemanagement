package com.zidiogroup9.expensemanagement.dtos;

import com.zidiogroup9.expensemanagement.entities.ExpenseCategory;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private String id;
    private Double amount;
    private String invoice;
    private String description;
    private Status status;
    private User user;
    private ExpenseCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
