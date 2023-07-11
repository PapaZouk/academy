package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.IPayable;
import org.kainos.ea.cli.SalesEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public List<IPayable> getEmployees() {
        Employee employee = new Employee(
                1,
                "Rafal",
                20000
        );
        SalesEmployee salesEmployee = new SalesEmployee(
                1,
                "Kordian",
                20000,
                1000,
                0.01f
        );

        Contractor contractor = new Contractor(
                "Andrzej",
                1000,
                10
        );

        List<IPayable> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(salesEmployee);
        employees.add(contractor);

        for (IPayable emp : employees) {
            System.out.println(emp.calcPay());
        }
        return employees;
    }
}
