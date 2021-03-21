package com.employeepayrollservice;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EmpPayrollJDBCOperations {

    public enum SalaryType {
        AVG, SUM, MAX, MIN;
    }

    SalaryType type2;

    private static PreparedStatement employeePayrollDataStatement;
    public static EmpPayrollJDBCOperations emp;
    public enum UpdateType {
        STATEMENT, PREPARED_STATEMENT;
    }

    UpdateType type;

    private static ConnectionJdbc c1 = null;
    public static String status = null;

    public static EmpPayrollJDBCOperations getInstance() {
        if (emp == null) {
            emp = new EmpPayrollJDBCOperations();
        }
        return emp;
    }

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

    public List<EmployeePayrollData> readEmployeePayrollData() throws EmployeePayrollJDBCException {
        List<EmployeePayrollData> empList = new ArrayList<EmployeePayrollData>();
        String sql = "Select * from employee_payroll;";
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            empList = this.getEmployeePayrollData(result);
        } catch (SQLException e) {
            throw new EmployeePayrollJDBCException("Cannot retrieve data!! \nInvalidDatatypeException thrown...!!",
                    EmployeePayrollJDBCException.ExceptionType.INVALID_DATA);
        }
        return empList;

    }

    public boolean UpdateSalary(UpdateType type) throws EmployeePayrollJDBCException {
        double salary = 3000000;
        String name = "Terissa";
        String dept = "Sales";
        boolean update = true;
        int res = 0;
        try (Connection con2 = getConnection()) {
            String type1 = type.toString();
            switch (type1) {
                case "STATMENT": {
                    String sql2 = String.format("UPDATE employee_payroll SET basic_pay = %.2f WHERE name = '%s';", salary,
                            name);
                    Statement statement = con2.createStatement();
                    res = statement.executeUpdate(sql2);
                    if (res == 0) {
                        update = false;
                        throw new EmployeePayrollJDBCException("Cannot update data!! \nUpdateFailException thrown...!!",
                                EmployeePayrollJDBCException.ExceptionType.UPDATE_FAILED);
                    }
                }
                case "PREPARED_STATEMENT": {
                    String sql2 = String.format(
                            "UPDATE employee_payroll SET basic_pay = ? WHERE name = ? AND department = ?;", salary, name,
                            dept);
                    PreparedStatement pstmnt = con2.prepareStatement(sql2);
                    pstmnt.setDouble(1, salary);
                    pstmnt.setString(2, name);
                    pstmnt.setString(3, dept);
                    res = pstmnt.executeUpdate();
                    pstmnt.close();
                    if (res == 0) {
                        update = false;
                        throw new EmployeePayrollJDBCException("Cannot update data!! \nUpdateFailException thrown...!!",
                                EmployeePayrollJDBCException.ExceptionType.UPDATE_FAILED);
                    }

                }
            }
        } catch (SQLException e) {
            update = false;
            throw new EmployeePayrollJDBCException("Cannot update data!! \nUpdateFailException thrown...!!",
                    EmployeePayrollJDBCException.ExceptionType.UPDATE_FAILED);
        }
        return update;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollData = null;
        if (employeePayrollDataStatement == null)
            this.preparedStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollData = getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollData;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet result) {
        List<EmployeePayrollData> empList = new ArrayList<>();
        try {
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
                Date startDate = result.getDate("start");
                EmployeePayrollData emp = new EmployeePayrollData(id, name, gender, basic_pay, phone, dept, add,
                        deductions, taxable_pay, tax, net_pay, startDate);
                empList.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // System.out.println(empList);
        return empList;
    }

    private void preparedStatementForEmployeeData() {
        try {
            Connection con = getConnection();
            String sql = "select * from employee_payroll where name = ?";
            employeePayrollDataStatement = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmployeePayrollJDBCException e) {
            e.printStackTrace();
        }
    }

    //uc5 for retriving data of employee from given range of join date
    public List<EmployeePayrollData> QueryForGivenDateRange(Date startDate, Date endDate) {
        String sql = String.format("select * from employee_payroll where start between '%s' and '%s';", startDate,
                endDate);
        List<EmployeePayrollData> empList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            empList = this.getEmployeePayrollData(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmployeePayrollJDBCException e1) {
            e1.printStackTrace();
        }
        return empList;
    }

    //uc6

    public Map<String, Double> QueryForSalaryByGender(SalaryType type) {
        String type2 = type.toString();
        String sql6 = "";
        Map<String, Double> SalaryMap = new HashMap<>();
        try (Connection connection = getConnection()) {
            switch (type2) {
                case "AVG": {
                    sql6 = "select gender,avg(basic_pay) as Retrived_Salary from employee_payroll group by gender;";
                }
                case "MAX": {
                    sql6 = "select gender,max(basic_pay) as Retrived_Salary from employee_payroll group by gender;";
                }
                case "MIN": {
                    sql6 = "select gender,min(basic_pay) as Retrived_Salary from employee_payroll group by gender;";
                }
                case "SUM": {
                    sql6 = "select gender,sum(basic_pay) as Retrived_Salary from employee_payroll group by gender;";
                }
            }
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql6);
            while (result.next()) {
                String gender = result.getString("gender");
                double salary = result.getDouble("Retrived_Salary");
                SalaryMap.put(gender, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmployeePayrollJDBCException e1) {
            e1.printStackTrace();
        }
        return SalaryMap;
    }

    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("   " + driverClass.getClass().getName());
        }
    }

}
