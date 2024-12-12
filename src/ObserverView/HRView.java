package ObserverView;

import EmployeeDatabase.Employee;
import SubjectModel.HRModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HRView {

    JFrame frame;

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    HRPanel hrPanel = new HRPanel();
    LogInPanel logInPanel = new LogInPanel();
    RegisterEmployeePanel registerEmployeePanel = new RegisterEmployeePanel();

    public HRView() {
        frame = new JFrame();
    }

    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(cardPanel);

        cardPanel.add(logInPanel, "Login");
        cardPanel.add(hrPanel, "HR");
        cardPanel.add(registerEmployeePanel, "Register");

        switchTo("Login");
        frame.setVisible(true);
    }

    public void switchTo(String name) {
        cardLayout.show(cardPanel, name);
        if (name.equalsIgnoreCase("Login")){
            frame.setSize(500, 200);
            frame.setLocationRelativeTo(null);
        }
        else if (name.equalsIgnoreCase("Register")){
            frame.setSize(400, 400);
        }
        else{
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
        }
    }

    public void setFieldsWithDetails(Employee selectedEmployee){
        hrPanel.getShowDetailsNameTextField().setText(selectedEmployee.getName());
        hrPanel.getShowDetailsPositionTextField().setText(selectedEmployee.getPosition().name());
        hrPanel.getShowDetailsSalaryTextField().setText(String.valueOf(selectedEmployee.getSalary()));
        hrPanel.getShowDetailsEmploymentPercentageTextField().setText(String.valueOf(selectedEmployee.getWorkingPercentage()));
        hrPanel.getShowDetailsPhoneTextField().setText(selectedEmployee.getPhoneNumber());
        hrPanel.getShowDetailsEmailTextField().setText(selectedEmployee.getEmail());
    }

    public HRPanel getHrPanel() {
        return hrPanel;
    }

    public LogInPanel getLogInPanel() {
        return logInPanel;
    }

    public RegisterEmployeePanel getRegisterEmployeePanel() {
        return registerEmployeePanel;
    }

    //
//    @Override
//    public void updateEmployeeDetails() {
//        setFieldsWithDetails(model.getSelectedEmployee());
//    }
//
//    @Override
//    public void updateSearchResult() {
//        hrPanel.resetTable();
//        List<Employee> currentSearchResult = model.getCurrentSearchResult();
//        for(Employee employee: currentSearchResult){
//            hrPanel.addEmployeeRow(employee);
//        }
//    }
//
//    @Override
//    public void updateFilteredResult() {
//        hrPanel.resetTable();
//        List<Employee> currentFilteredResult = model.getCurrentFilteredResult();
//        for(Employee employee : currentFilteredResult){
//            hrPanel.addEmployeeRow(employee);
//        }
//    }
//
//    @Override
//    public void updateEmployee(){
//        hrPanel.resetTable();
//        List<Employee> currentSearchResult = model.getCurrentSearchResult();
//        for(Employee employee : currentSearchResult){
//            hrPanel.addEmployeeRow(employee);
//        }
//        setFieldsWithDetails(model.getChangedEmployee()); //?
////        updateEmployeeDetails(model.getSelectedEmployee());
//    }
}
