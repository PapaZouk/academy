package org.kainos.ea.cli;

public class DeliveryEmployee extends Employee {
    public DeliveryEmployee(int employeeId, String name, double salary) {
        super(employeeId, name, salary);
    }

    @Override
    public double calcPay() {
        return getSalary() / 12 + 1000;
    }
}
