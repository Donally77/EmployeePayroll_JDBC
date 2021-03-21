package com.employeepayrollservice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmpPayrollJDBCOperations {

    private static ConnectionJdbc c1 = null;
    public static String status = null;

    public EmpPayrollJDBCOperations() {
        c1 = new ConnectionJdbc();
    }

    public static Connection getConnection() throws EmployeePayrollJDBCException {
        String[] credentials = c1.getCredentials();
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new EmployeePayrollJDBCException("Cannot connect to the JDBC Driver!! \nDriverException thrown...",
                    EmployeePayrollJDBCException.ExceptionType.CANNOT_LOAD_DRIVER);
        }
        listDrivers();
        try {
            System.out.println("Connecting to database:" + credentials[0]);
            connection = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
            status = "Connection is Successfull";
            System.out.println(status);
        } catch (Exception e) {
            throw new EmployeePayrollJDBCException("Unable to Connect!! \nWrongLoginCredentialsException thrown... ",
                    EmployeePayrollJDBCException.ExceptionType.WRONG_CREDENTIALS);
        }
        return connection;
    }

    public static List<EmployeePayrollDataJDBC> showTable() throws EmployeePayrollJDBCException {
        List<EmployeePayrollDataJDBC> empList = new ArrayList<EmployeePayrollDataJDBC>();
        String sql = "Select * from employee_payroll;";
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String gender = result.getString("gender");
                double basic_pay = result.getDouble("basic_pay");
                String phone = result.getString("phone_no");
                String dept = result.getString("department");
                String add = result.getString("address");
                double deductions = result.getDouble("deductions");
                double taxable_pay = result.getDouble("taxable_pay");
                double tax = result.getDouble("tax");
                double net_pay = result.getDouble("net_pay");
                LocalDate startDate = result.getDate("start").toLocalDate();
                EmployeePayrollDataJDBC emp = new EmployeePayrollDataJDBC(id, name, gender, basic_pay, phone, dept, add,
                        deductions, taxable_pay, tax, net_pay, startDate);
                empList.add(emp);
            }
        } catch (SQLException e) {
            throw new EmployeePayrollJDBCException("Cannot retrieve data!! \nInvalidDatatypeException thrown...!!",
                    EmployeePayrollJDBCException.ExceptionType.INVALID_DATA);
        }
        return empList;

    }


    //uc3 for update salary of employee
    public static boolean UpdateSalary() throws EmployeePayrollJDBCException {
        double salary = 3000000;
        String name = "Terissa";
        boolean update = true;
        String sql2 = String.format("UPDATE employee_payroll SET basic_pay = %.2f WHERE name = '%s';", salary, name);
        int res = 0;
        try (Connection con2 = getConnection()) {
            Statement statement = con2.createStatement();
            res = statement.executeUpdate(sql2);
            if (res == 0) {
                update = false;
                throw new EmployeePayrollJDBCException("Cannot update data!! \nUpdateFailException thrown...!!",
                        EmployeePayrollJDBCException.ExceptionType.UPDATE_FAILED);
            }
        } catch (SQLException e) {
            update = false;
            throw new EmployeePayrollJDBCException("Cannot update data!! \nUpdateFailException thrown...!!",
                    EmployeePayrollJDBCException.ExceptionType.UPDATE_FAILED);
        }
        return update;
    }



    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("   " + driverClass.getClass().getName());
        }
    }

}
