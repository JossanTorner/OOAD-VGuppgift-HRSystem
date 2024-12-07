package HR.Commands;

public class EmployeeInfoChange {

    String employeeVariable;
    Object oldValue;
    Object newValue;

    public EmployeeInfoChange(String employeeVariable, Object oldValue, Object newValue) {
        this.employeeVariable = employeeVariable;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
