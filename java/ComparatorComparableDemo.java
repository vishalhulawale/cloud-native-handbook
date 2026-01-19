import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee implements Comparable<Employee> {
    String name;
    int id;

    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }
}

class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.name.compareTo(e2.name);
    }
}

public class ComparatorComparableDemo {

    public static List<Employee>  employeeList = new ArrayList<>(List.of(
        new Employee("John", 28),
        new Employee("Jane", 32),
        new Employee("Doe", 25)
    ));

    public static void main(String[] args) {
        System.out.println("Before Sorting:");
        for (Employee emp : employeeList) {
            System.out.println(emp.name + " - " + emp.id);
        }

        Collections.sort(employeeList);

        System.out.println("\nAfter Sorting by natural order (id):");
        for (Employee emp : employeeList) {
            System.out.println(emp.name + " - " + emp.id);
        }

        employeeList.sort(new NameComparator());
        System.out.println("\nAfter Sorting by name:");
        for (Employee emp : employeeList) {
            System.out.println(emp.name + " - " + emp.id);
        }
    }
}
