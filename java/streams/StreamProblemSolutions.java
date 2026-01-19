package streams;

import models.Department;
import models.Employee;
import repository.DepartmentRepository;

import java.util.*;
import java.util.stream.Collectors;

public class StreamProblemSolutions {
    public static void main(String[] args) {

        List<Department> departments = DepartmentRepository.findAll();

        // 1. Create a map of department name to list of employee names.
        Map<String, List<String>> departmentWiseEmployeeList = departments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Department::deptName,
                                Collectors.flatMapping(
                                        d -> d.employees().stream().map(Employee::name),
                                        Collectors.toList())
                        )
                );
        System.out.println("Department wise Employees: " + departmentWiseEmployeeList);

        // 2. Compute average salary per department.
        Map<String, Double> averageSalaryPerDepartment =
                departments.stream().collect(
                        Collectors.groupingBy(
                                Department::deptName,
                                Collectors.flatMapping(d -> d.employees().stream(),
                                        Collectors.averagingDouble(Employee::salary)
                                )
                        ));
        System.out.println("\nDepartment wise avg salary: " + averageSalaryPerDepartment);

        // 3. Calculate total salary payout per department.
        Map<String, Double> totalSalaryPerDepartment = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::deptName,
                        Collectors.flatMapping(
                                d -> d.employees().stream(),
                                Collectors.summingDouble(Employee::salary))
                ));
        System.out.println("\nDepartment wise total salary: " + totalSalaryPerDepartment);

        // 5. Group employees by department but include only employees earning > 90k.
        Map<String, List<String>> departmentWiseEmployeeListSalaryGt90k = departments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Department::deptName,
                                Collectors.flatMapping(
                                        d -> d.employees().stream().filter(e -> e.salary() > 90000).map(Employee::name),
                                        Collectors.toList())
                        )
                );
        System.out.println("\nDepartment wise employees with salary more than 90k: " + departmentWiseEmployeeListSalaryGt90k);
        // 6. Find the highest-paid employee in each department.
        Map<String, String> departmentWiseHighestPaidEmployee = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::deptName,
                        Collectors.flatMapping(d -> d.employees().stream(),
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingDouble(Employee::salary)),
                                        e -> e.map(Employee::name).orElse("N/A")
                                )
                        )
                ));
        System.out.println("\n Department wise highest paid employee: " + departmentWiseHighestPaidEmployee);
        // 7. Get the name of the lowest-paid employee per department.
        Map<String, String> departmentWiseLowestPaidEmployee = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::deptName,
                        Collectors.flatMapping(d -> d.employees().stream(),
                                Collectors.collectingAndThen(
                                        Collectors.minBy(Comparator.comparingDouble(Employee::salary)),
                                        e -> e.map(Employee::name).orElse("N/A")
                                ))
                ));
        System.out.println("\n Department wise lowest paid employee: " + departmentWiseLowestPaidEmployee);
        // 8. For each department, return top 2 highest-paid employees.
        Map<String, List<String>> departmentWiseTop2HighestPaidEmployees = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::deptName,
                        Collectors.flatMapping(
                                d -> d.employees().stream().sorted(Comparator.comparing(Employee::salary)).limit(2).map(Employee::name),
                                Collectors.toList())
                ));
        System.out.println("\n Department wise top 2 paid employee: " + departmentWiseTop2HighestPaidEmployees);
        // 9. Collect all unique skills per department.

        Map<String, Set<String>> departmentWiseUniqueSkills = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::deptName,
                        Collectors.flatMapping(
                                d -> d.employees().stream().map(Employee::skills).flatMap(List::stream),
                                Collectors.toSet())
                ));
        System.out.println("\n Department wise unique skills: " + departmentWiseUniqueSkills);

        // 11. Partition employees into: salary ≥ 100k, salary < 100k inside each department.
        Map<Boolean, List<String>> employeesPartitionedBySalary = departments.stream().collect(
                Collectors.flatMapping(d -> d.employees().stream(),
                        Collectors.partitioningBy(
                                e -> e.salary() >= 100000,
                                Collectors.mapping(Employee::name, Collectors.toList()))
                ));
        System.out.println("\n Employees partitioned by salary >= 100k and < 100k: " + employeesPartitionedBySalary);

        // 12. Join employee names per department in alphabetical order.
        Map<String, String> departmentWiseEmployeeListCommaSeparated = departments.stream().
                collect(Collectors.groupingBy(
                                Department::deptName,
                                Collectors.flatMapping(d -> d.employees().stream(),
                                        Collectors.mapping(
                                                Employee::name,
                                                Collectors.joining(",", "[", "]")
                                        )
                                )
                        )

                );
        System.out.println("\nDepartment wise Employees: " + departmentWiseEmployeeListCommaSeparated);
    }

}
