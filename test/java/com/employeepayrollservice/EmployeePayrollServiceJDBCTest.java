package com.employeepayrollservice;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeePayrollServiceJDBCTest {

    EmpPayrollJDBCOperations e1 = new EmpPayrollJDBCOperations();

    @Test
    public void ConnectToTheDatabase() {
        try {
            e1.getConnection();
            assertEquals("Connection is Successfull", e1.status);
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenDBShoulRetrieveContentsFromTheTable() {
        try {
            List<EmployeePayrollDataJDBC> list1 = e1.showTable();
            assertEquals(5, list1.size());
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }

    }


}
