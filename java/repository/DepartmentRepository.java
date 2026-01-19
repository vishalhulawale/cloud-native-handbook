package repository;

import models.Department;
import models.Employee;

import java.math.BigDecimal;
import java.util.List;

public class DepartmentRepository {

    public static List<Department> findAll() {
        return List.of(
                new Department("Engineering", 101,
                        List.of(
                                new Employee("Alice", 1, List.of("Java", "Spring", "Docker"), 90000.0),
                                new Employee("Bob", 2, List.of("JavaScript", "React", "Node.js"), 85000.0),
                                new Employee("Charlie", 3, List.of("Java", "Microservices", "Kafka"), 95000.0)
                        )),

                new Department("Human Resources", 102,
                        List.of(
                                new Employee("Diana", 4, List.of("Recruitment", "Payroll"), 60000.0),
                                new Employee("Evan", 5, List.of("Employee Relations"), 58000.0)
                        )),

                new Department("Finance", 103,
                        List.of(
                                new Employee("Frank", 6, List.of("Accounting", "Taxation"), 70000.0),
                                new Employee("Grace", 7, List.of("Auditing", "Compliance"), 72000.0)
                        )),

                new Department("Marketing", 104,
                        List.of(
                                new Employee("Hannah", 8, List.of("SEO", "Content Marketing"), 65000.0),
                                new Employee("Ian", 9, List.of("Brand Management", "Social Media"), 68000.0)
                        )
                ));
    }

}
