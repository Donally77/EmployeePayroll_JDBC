package com.employeepayrollservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeePayrollServiceJDBCTest {

    EmpPayrollJDBCOperations e1 = new EmpPayrollJDBCOperations();

    @Test
    public void ConnectToTheDatabase() {
        String status;
        try {
            status = e1.getConnection();
            assertEquals("Connection is Successfull", status);
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }

    }


}
