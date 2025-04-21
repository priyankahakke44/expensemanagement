package com.zidiogroup9.expensemanagement.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters long.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@]).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and the '@' symbol."
    )
    private String newPassword;
    private String confirmPassword;
}
