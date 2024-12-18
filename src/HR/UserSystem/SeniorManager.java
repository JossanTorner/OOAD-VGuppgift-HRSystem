package HR.UserSystem;

import EmployeeDatabase.EmployeeDatabase;
import EmployeeDatabase.Employee;
import HR.Commands.Command;
import HR.Commands.EmployeeInfoChange;
import HR.Commands.ChangeEmployeeCommand;

import java.util.ArrayList;
import java.util.List;

public class SeniorManager extends AppUser implements AuthorizedManager {

    EmployeeDatabase employeeDatabase;
    List<Command> commandHistory;

    public SeniorManager(String name, String password) {
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
    public void undoUpdate(){
        if(!commandHistory.isEmpty()){
            Command latestCommand = commandHistory.getLast();
            latestCommand.undo();
            commandHistory.removeLast();
        }
        else{
            throw new IllegalArgumentException("Nothing to undo");
        }
    }

    @Override
    public List<Command> getCommandHistory(){
        return commandHistory;
    }

    @Override
    public void clearCommandHistory(){
        commandHistory.clear();
    }
}
