package HR.UserSystem;

import EmployeeDatabase.Employee;
import EmployeeDatabase.Position;
import HR.Commands.EmployeeInfoChange;

import java.util.List;

public interface AuthorizedManager {

    void makeUpdate(Employee employee, List<EmployeeInfoChange> changesMade);

    void undoCommand();

    void createNewEmployee(String name, long id, Position position, double salary, int employmentPercentage, String email, String phoneNumber);

}
