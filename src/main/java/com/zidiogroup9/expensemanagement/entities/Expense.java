package com.zidiogroup9.expensemanagement.entities;

import com.zidiogroup9.expensemanagement.entities.enums.ApproverRole;
import com.zidiogroup9.expensemanagement.entities.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double amount;
    @Column(length = 1000)
    private String invoice;
    private String description;

    @Enumerated(EnumType.STRING)
    private ApproverRole approvedBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
