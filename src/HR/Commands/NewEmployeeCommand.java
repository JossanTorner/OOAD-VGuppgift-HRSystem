package HR.Commands;

import EmployeeDatabase.Employee;
import java.util.HashMap;

public class NewEmployeeCommand implements Command{

    HashMap<Long, Employee> employees;
    Employee newEmployee;

    public NewEmployeeCommand(HashMap<Long, Employee> employeeList, Employee newEmployee) {
        this.employees = employeeList;
        this.newEmployee = newEmployee;
    }

    @Override
    public void execute() {
        employees.put(newEmployee.getEmployeeId(), newEmployee);
    }

    @Override
    public void undo() {
        employees.remove(newEmployee.getEmployeeId());
    }
}
