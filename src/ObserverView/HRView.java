package ObserverView;

import EmployeeDatabase.Employee;
import SubjectModel.HRModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HRView implements EmployeeDetailsObserver, SearchResultObserver, FilterPositionObserver, EmployeeChangeObserver {

    JFrame frame;

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    HRPanel hrPanel = new HRPanel();
    LogInPanel logInPanel = new LogInPanel();


    HRModel model;

    public HRView(HRModel model) {
        this.model = model;
        registerAsObserver();
        frame = new JFrame();
    }

    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(cardPanel);

        cardPanel.add(logInPanel, "Login");
        cardPanel.add(hrPanel, "HR");

        switchTo("Login");
        populateAllEmployees();
    }

    public void switchTo(String name) {
        cardLayout.show(cardPanel, name);
    }

    private void registerAsObserver(){
        model.registerDetailsObserver(this);
        model.registerSearchObserver(this);
        model.registerPositionSearchObserver(this);
        model.registerChangeObserver(this);
    }

    private void populateAllEmployees() {
        model.setSearchResultByName("");
    }

    public void updateEmployeeDetails(Employee selectedEmployee){
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

    @Override
    public void updateEmployeeDetails() {
        updateEmployeeDetails(model.getSelectedEmployee());
    }

    @Override
    public void updateSearchResult() {
        hrPanel.resetTable();
        List<Employee> currentSearchResult = model.getCurrentSearchResult();
        for(Employee employee: currentSearchResult){
            hrPanel.addEmployeeRow(employee);
        }
    }

    @Override
    public void updateFilterSearch() {
        hrPanel.resetTable();
        List<Employee> currentFilteredResult = model.getCurrentFilteredResult();
        for(Employee employee : currentFilteredResult){
            hrPanel.addEmployeeRow(employee);
        }
    }

    @Override
    public void updateEmployeeChange(){
        hrPanel.resetTable();
        List<Employee> currentSearchResult = model.getCurrentSearchResult();
        for(Employee employee : currentSearchResult){
            hrPanel.addEmployeeRow(employee);
        }
        updateEmployeeDetails(model.getChangedEmployee()); //?
//        updateEmployeeDetails(model.getSelectedEmployee());
    }
}
