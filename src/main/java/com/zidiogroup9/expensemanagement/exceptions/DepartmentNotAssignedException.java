package com.zidiogroup9.expensemanagement.exceptions;

public class DepartmentNotAssignedException extends RuntimeException{
    public DepartmentNotAssignedException(String message){
        super(message);
    }
}
