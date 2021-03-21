package com.employeepayrollservice;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeePayrollServiceTest {


    // test method to check if the employee data is written to the file

    Date d1 = Date.valueOf("2018-01-03");
    Date d2 = Date.valueOf("2020-05-21");
    Date d3 = Date.valueOf("2019-11-13");

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile() {
        EmployeePayrollData[] empArray = {
                new EmployeePayrollData(1, "Bill", "M", 3000000, null, "", null, 0, 0, 0, 0, d1),
                new EmployeePayrollData(3, "Charlie", "M", 300000, null, "", null, 0, 0, 0, 0, d2),
                new EmployeePayrollData(6, "Terissa", "F", 3000000, null, "", null, 0, 0, 0, 0, d3) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        assertEquals(7, entries);
    }

    // test method to check if the employee data is written to the file

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile_AndPrintTheSame() {
        EmployeePayrollData[] empArray = {
                new EmployeePayrollData(1, "Bill", "M", 3000000, null, "", null, 0, 0, 0, 0, d1),
                new EmployeePayrollData(3, "Charlie", "M", 300000, null, "", null, 0, 0, 0, 0, d2),
                new EmployeePayrollData(6, "Terissa", "F", 3000000, null, "", null, 0, 0, 0, 0, d3) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        // employeePayrollService.printData(IOService.FILE_IO); // print method called
        assertEquals(7, entries);
    }

    // test method to check for print function in EmployeePayrollFileIOOperations

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile_AndReadTheEmployeePayrollFile() {
        EmployeePayrollData[] empArray = {
                new EmployeePayrollData(1, "Bill", "M", 3000000, null, "", null, 0, 0, 0, 0, d1),
                new EmployeePayrollData(3, "Charlie", "M", 300000, null, "", null, 0,0, 0, 0, d2),
                new EmployeePayrollData(6, "Terissa", "F", 3000000, null, "", null, 0, 0, 0, 0, d3) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);//
        List<EmployeePayrollData> employeeList = employeePayrollService.readData(EmployeePayrollService.IOService.DB_IO);
        assertEquals(7, entries);
    }

}
