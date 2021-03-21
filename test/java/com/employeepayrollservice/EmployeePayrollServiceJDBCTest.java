package com.employeepayrollservice;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
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

    @Test
    public void givebDBShouldUpdateSalaryUsingPreparedStatementOfAnEmployee_ShouldBeInSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService
                .readEmployeePayrollDataDB(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalaryUsingPrepareStatement("Terissa", 3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terissa", 3000000.00);
        assertTrue(result);
    }

    @Test
    public void givenDBShoulRetreiveEmployeesForASpecficDateRange() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(EmployeePayrollService.IOService.DB_IO);
        Date startDate = Date.valueOf("2018-01-01");
        Date endDate = Date.valueOf(LocalDate.now());
        List<EmployeePayrollData> empList = employeePayrollService.getEmployeeForDateRange(EmployeePayrollService.IOService.DB_IO,
                startDate, endDate);
        assertEquals(7, empList.size());
    }


}
