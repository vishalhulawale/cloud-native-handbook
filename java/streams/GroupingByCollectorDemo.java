package streams;

import models.Department;
import repository.DepartmentRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupingByCollectorDemo {


    public static void main(String[] args) {

        List<Department> departments = DepartmentRepository.findAll();

        // 1. Simple Grouping By
        Map<String, Integer> employeeCountByDept = departments.stream()
                .collect(
                        Collectors.groupingBy(
                                Department::deptName,
                                Collectors.summingInt(d -> d.employees().size())
                        )
                );
        System.out.println("Employee Count By Department: " + employeeCountByDept);

        // 2. Multi-level Grouping By
        Map<String, Set<String>> skillsByDept = departments.stream()
                .collect(
                        Collectors.groupingBy(
                                Department::deptName,
                                Collectors.flatMapping(
                                        d -> d.employees().stream().flatMap(
                                                e -> e.skills().stream()
                                        ),
                                        Collectors.toSet()
                                )

                        )
                );
        System.out.println("Skills By Department: " + skillsByDept);
    }
}
