package EmployeeDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeDatabase {

    HashMap<Long, Employee> employees = new HashMap<>();

    public EmployeeDatabase() {
        DataLoader dataLoader = new DataLoader();
        loadMap(dataLoader);
    }

    public void loadMap(DataLoader dataLoader) {
        dataLoader.getEmployees().forEach(employee -> {
            employees.put(employee.getEmployeeId(), employee);
        });
    }

    public List<Employee> searchByID(long id) {
        List<Employee> result = new ArrayList<>();
        if (employees.containsKey(id)) {
            result.add(employees.get(id));
        }
        return result;
    }

    public List<Employee> searchByName(String search) {
        List<Employee> matchingEmployees = new ArrayList<>();
        String temp = search.toLowerCase();
        for (Employee employee : employees.values()) {
            if (employee.getName().toLowerCase().contains(temp.trim())) {

                matchingEmployees.add(employee);
            }
        }
        return matchingEmployees;
    }
}
