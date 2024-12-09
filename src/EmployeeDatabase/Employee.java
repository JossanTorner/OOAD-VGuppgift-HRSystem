package EmployeeDatabase;

public class Employee {

    private final long employeeId;
    private String name;
    private double salary;
    private Position position;
    private String email;
    private String phoneNumber;
    private int workingPercentage;

    public Employee(long employeeId, String name, double salary, Position position, String email, String phoneNumber, int workingPercentage) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.workingPercentage = workingPercentage;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getWorkingPercentage() {
        return workingPercentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWorkingPercentage(int workingPercentage) {
        this.workingPercentage = workingPercentage;
    }

    @Override
   public String toString(){
        return "EmplymentID : " + employeeId + " " + name + " tjänar: " + salary + " position: " + position;
   }
}
