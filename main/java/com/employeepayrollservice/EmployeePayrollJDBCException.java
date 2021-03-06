package com.employeepayrollservice;

public class EmployeePayrollJDBCException extends Exception {

    public enum ExceptionType {
        WRONG_CREDENTIALS, CANNOT_LOAD_DRIVER, INVALID_DATA,UPDATE_FAILED;
    }

    ExceptionType type;

    public EmployeePayrollJDBCException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
