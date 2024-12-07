package HR.UserSystem;

import EmployeeDatabase.Employee;
import HR.Commands.EmployeeInfoChange;

import java.util.List;

public interface AuthorizedManager {

    void makeUpdate(Employee employee, List<EmployeeInfoChange> changesMade);

    void undoUpdate();

}
