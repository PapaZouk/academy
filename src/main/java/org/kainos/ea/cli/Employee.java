package org.kainos.ea.cli;

public class Employee implements IPayable, IPermanent {
    private int employeeId;

    private String name;
    private double salary;


    public Employee(int employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double calcPay() {
        return getSalary()/12;
    }

    @Override
    public double calcBonus() {
        return salary * 0.1;
    }
}
