package HR.UserSystem;

import EmployeeDatabase.EmployeeDatabase;
import EmployeeDatabase.Employee;
import HR.Commands.Command;
import HR.Commands.EmployeeInfoChange;
import HR.Commands.ChangeEmployeeCommand;
import HR.Commands.NewEmployeeCommand;
import EmployeeDatabase.*;

import java.util.ArrayList;
import java.util.List;



public class SeniorHRManager extends AppUser implements AuthorizedManager {

    EmployeeDatabase employeeDatabase;
    List<Command> commandHistory;

    public SeniorHRManager(String name, String password) {
        super(name, password);

        commandHistory = new ArrayList<>();
        employeeDatabase = new EmployeeDatabase();
    }

    @Override
    public void makeUpdate(Employee employee, List<EmployeeInfoChange> changesMade){
        Command command = new ChangeEmployeeCommand(employee, changesMade);
        command.execute();
        commandHistory.add(command);
    }

    @Override
    public void createNewEmployee(String name, long id, Position position, double salary, int employmentPercentage, String email, String phoneNumber){
        Employee newEmployee = new Employee(id, name, salary, position, email, phoneNumber, employmentPercentage);
        Command command = new NewEmployeeCommand(employeeDatabase.getEmployees(), newEmployee);
        command.execute();
        commandHistory.add(command);
    }

    @Override
    public void undoCommand(){
        if(!commandHistory.isEmpty()){
            Command latestCommand = commandHistory.getLast();
            latestCommand.undo();
            commandHistory.removeLast();
        }
        else{
            throw new IllegalArgumentException("Nothing to undo");
        }
    }
}
