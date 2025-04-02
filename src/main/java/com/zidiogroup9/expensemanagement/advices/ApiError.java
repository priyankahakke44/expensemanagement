package com.zidiogroup9.expensemanagement.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;
    private List<String> suberror;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(String message, HttpStatus statusCode) {
        this();
        this.message = message;
        this.status = statusCode;
    }
}
