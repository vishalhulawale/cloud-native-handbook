package streams;

import models.Department;
import models.Employee;
import repository.DepartmentRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicCollectorsDemo {

    public static void main(String[] args) {

        List<Department> departments = DepartmentRepository.findAll();

        // 1. toList
        List<String> deptNames = departments.stream()
                .map(Department::deptName)
                .toList();
        System.out.println("Department Names: " + deptNames);

        // 2. toSet
        Set<String> skillSet = departments.stream().map(Department::employees)
                .flatMap(List::stream).map(Employee::skills)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        System.out.println("Unique Skills: "+ skillSet);

        // 3. toCollection
        LinkedList<String> list = departments.stream().map(Department::deptName).collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Department Names: " + list);
    }
}
