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
        String sql = "Select * from employeepayroll;";
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String phone = result.getString("phone_number");
                String address = result.getString("address");
                String dept = result.getString("department");
                String gender = result.getString("Gender");
                double basic_pay = result.getDouble("basic_pay");
                double deductions = result.getDouble("deductions");
                double taxable_pay = result.getDouble("taxable_pay");
                double tax = result.getDouble("tax");
                double net_pay = result.getDouble("net_pay");
                LocalDate startDate = result.getDate("start").toLocalDate();
                EmployeePayrollDataJDBC emp = new EmployeePayrollDataJDBC(id, name, phone, address, dept, gender, basic_pay, deductions, taxable_pay, tax, net_pay, startDate);
                empList.add(emp);
            }
        } catch (SQLException e) {
            throw new EmployeePayrollJDBCException("Cannot retrieve data!! \nInvalidDatatypeException thrown...!!",
                    EmployeePayrollJDBCException.ExceptionType.INVALID_DATA);
        }
        return empList;

    }

    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("   " + driverClass.getClass().getName());
        }
    }

}
