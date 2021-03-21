package com.employeepayrollservice;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


    //uc2 for select statement
    @Test
    public void givenDBShoulRetrieveContentsFromTheTable() {
        try {
            List<EmployeePayrollData> list1 = e1.readEmployeePayrollData();
            assertEquals(7, list1.size());
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenDBShouldUpdateSalaryOfAnEmployee() {
        try {
            assertTrue(e1.UpdateSalary(EmpPayrollJDBCOperations.UpdateType.STATEMENT));
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givebDBShouldUpdateSalaryUsingPreparedStatementOfAnEmployee() {
        try {
            assertTrue(e1.UpdateSalary(EmpPayrollJDBCOperations.UpdateType.PREPARED_STATEMENT));
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }
    }



}
