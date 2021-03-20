package com.employeepayrollservice;

import java.time.LocalDate;

public class EmployeePayrollDataJDBC {

    public int id;
    public String name;
    public String gender;
    public double basic_pay;
    public String phone;
    public String dept;
    public String add;
    public double deductions;
    public double taxable_pay;
    public double tax;
    public double net_pay;
    public LocalDate startDate;

    public EmployeePayrollDataJDBC(int id, String name,String phone,String dept,String add, String gender, double basic_pay,
                                    double deductions, double taxable_pay, double tax, double net_pay, LocalDate start) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.add = add;
        this.dept = dept;
        this.gender = gender;
        this.basic_pay = basic_pay;
        this.deductions = deductions;
        this.taxable_pay = taxable_pay;
        this.tax = tax;
        this.net_pay = net_pay;
        this.startDate = start;
    }

    @Override
    public String toString() {
        return "\nID = " + id + ", Name = " + name + ", Gender = " + gender + ", Basic Pay = " + basic_pay + ", Phone = "
                + phone + ", Department=" + dept + ". Address=" + add + ", Deductions=" + dept + ", Taxable Pay ="
                + taxable_pay + ", Tax=" + tax + ", Net Pay = " + net_pay + ", Start Date =" + startDate;
    }
}
