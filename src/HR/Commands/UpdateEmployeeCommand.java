package HR.Commands;

import EmployeeDatabase.Employee;
import EmployeeDatabase.Position;

import java.util.ArrayList;
import java.util.List;

public class UpdateEmployeeCommand implements Command {

    Employee employee;
    List<EmployeeInfoChange> changesMade;

    public UpdateEmployeeCommand(Employee employee, List<EmployeeInfoChange> changesMade) {
        this.employee = employee;
        this.changesMade = changesMade;
    }

    public Object getOldValue(String employeeVariable){
        switch(employeeVariable.toUpperCase()){
            case "NAME" ->{
                return employee.getName();
            }
            case "POSITION" ->{
                return employee.getPosition();
            }
            case "SALARY" ->{
                return employee.getSalary();
            }
            case "WORKING_PERCENTAGE" ->{
                return employee.getWorkingPercentage();
            }
            case "EMAIL" ->{
                return employee.getEmail();
            }
            case "PHONE_NUMBER" ->{
                return employee.getPhoneNumber();
            }
            default ->{
                return null;
            }
        }
    }

    public void makeChange(String employeeVariable, Object value) {
        switch(employeeVariable.toUpperCase()){
            case "NAME" ->{
                employee.setName((String) value);
            }
            case "POSITION" ->{
                employee.setPosition((Position) value);
            }
            case "SALARY" ->{
                employee.setSalary((Double) value);
            }
            case "WORKING_PERCENTAGE" ->{
                employee.setWorkingPercentage((int) value);
            }
            case "EMAIL" ->{
                employee.setEmail((String) value);
            }
            case "PHONE_NUMBER" ->{
                employee.setPhoneNumber((String) value);
            }
        }
    }

    @Override
    public void execute() {
        for(EmployeeInfoChange change: changesMade) {
            makeChange(change.employeeVariable, change.newValue);
        }
    }

    @Override
    public void undo() {
        for(EmployeeInfoChange change: changesMade) {
            makeChange(change.employeeVariable, change.oldValue);
        }
    }
}
