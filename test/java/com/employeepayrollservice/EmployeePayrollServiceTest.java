package com.employeepayrollservice;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeePayrollServiceTest {


    // test method to check if the employee data is written to the file

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile() {
        EmployeePayrollData[] empArray = { new EmployeePayrollData(200456, "Donally", 500000.0),
                new EmployeePayrollData(200457, "Pooja", 200000.0),
                new EmployeePayrollData(200551, "Swati", 800000.0) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        assertEquals(3, entries);
    }



    // test method to check for print function in EmployeePayrollFileIOOperations

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile_AndPrintTheSame() {
        EmployeePayrollData[] empArray = { new EmployeePayrollData(200456, "Donally", 500000.0),
                new EmployeePayrollData(200457, "Pooja", 200000.0),
                new EmployeePayrollData(200551, "Swati", 800000.0) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO); // print method called
        assertEquals(3, entries);
    }



    // test method to check for read data function

    @Test
    public void given3EmployeeEntries_ShouldMatchTheEmployeeEntries_WhenWrittenToTheFile_AndReadTheEmployeePayrollFile() {
        EmployeePayrollData[] empArray = { new EmployeePayrollData(200456, "Donally", 500000.0),
                new EmployeePayrollData(200457, "Pooja", 200000.0),
                new EmployeePayrollData(200551, "Swati", 800000.0) };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(empArray));
        employeePayrollService.writeData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        List<EmployeePayrollData> employeeList = employeePayrollService.readData(EmployeePayrollService.IOService.FILE_IO);
        System.out.println(employeeList);
        assertEquals(3, entries);
    }

}
