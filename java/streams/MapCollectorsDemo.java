package streams;

import models.Department;
import repository.DepartmentRepository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MapCollectorsDemo {


    public static void main(String[] args) {

        List<Department> departments = DepartmentRepository.findAll();

        Map<Integer, String> departmentMap = departments.stream().collect(Collectors.toMap(
                Department::deptId,
                Department::deptName
        ));
        System.out.println("Department Map: " + departmentMap);

        TreeMap<String, Integer> departmentMapSorted = departments.stream().collect(Collectors.toMap(
                Department::deptName,
                Department::deptId,
                (e1, e2) -> e1,
                TreeMap::new
        ));
        System.out.println("Department Map [Sorted]: " + departmentMapSorted);
    }
}
