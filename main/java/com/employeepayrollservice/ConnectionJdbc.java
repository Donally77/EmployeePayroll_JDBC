package com.employeepayrollservice;

public class ConnectionJdbc {

    String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
    String userName = "root";
    String password = "Dona";

    public String[] getCredentials() {
        String[] confidential = { jdbcURL, userName, password };
        return confidential;
    }
}
