package HR.Commands;

import EmployeeDatabase.Employee;
import java.util.HashMap;

public class NewEmployeeCommand implements Command{

    HashMap<Long, Employee> employeeList;
    Employee newEmployee;

    public NewEmployeeCommand(HashMap<Long, Employee> employeeList, Employee newEmployee) {
        this.employeeList = employeeList;
        this.newEmployee = newEmployee;
    }

    @Override
    public void execute() {
        employeeList.put(newEmployee.getEmployeeId(), newEmployee);
    }

    @Override
    public void undo() {
        employeeList.remove(newEmployee.getEmployeeId());
    }
}
