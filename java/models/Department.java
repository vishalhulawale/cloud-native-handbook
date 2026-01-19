package models;

import java.util.List;

public record Department(String deptName, int deptId, List<Employee> employees) {

}
