package com.zidiogroup9.expensemanagement.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDto {
    @Size(min = 3,message = "The name must consist of more than three letters")
    private String name;
    private MultipartFile image;
}
