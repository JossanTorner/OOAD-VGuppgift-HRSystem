package Controller;

import EmployeeDatabase.Employee;
import EmployeeDatabase.Position;
import HR.Commands.EmployeeInfoChange;
import HR.UserSystem.AppUser;
import HR.UserSystem.UserDatabase;
import HR.UserSystem.AuthorizedManager;
import ObserverView.*;
import SubjectModel.HRModel;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class HRController implements EmployeeChangeObserver, EmployeeDetailsObserver, FilterResultObserver, SearchResultObserver {

    private final HRView view;
    private final HRModel model;
    private AppUser appUser;
    private boolean makingChanges = false;

    public HRController(HRModel model) {
        this.model = model;

        model.registerChangeObserver(this);
        model.registerDetailsObserver(this);
        model.registerSearchObserver(this);
        model.registerFilterObserver(this);

        view = new HRView();
        view.init();

        addListenersToHRPanel();
        addListenersToLoginPanel();
        addListenersToRegisterPanel();
        populateAllEmployees();
    }

    public void addListenersToLoginPanel(){
        LogInPanel logInPanel = view.getLogInPanel();

        logInPanel.getExitButton().addActionListener(e->{
            System.exit(0);
        });

        logInPanel.getLoginButton().addActionListener(e->{

            String name = logInPanel.getUserField().getText();
            System.out.println(name);
            String password = new String(logInPanel.getPasswordField().getPassword());
            System.out.println(password);

            AppUser loggedIn = UserDatabase.findUserInDatabase(name, password);

            if (loggedIn != null) {
                appUser = loggedIn;
                System.out.println("Logged in: " + appUser.getName());
                view.switchTo("HR");
            }
            else{
                JOptionPane.showMessageDialog(view.getLogInPanel(), "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                logInPanel.getPasswordField().setText("");
                logInPanel.getUserField().setText("");
            }
        });
    }

    public void addListenersToHRPanel(){
        HRPanel hrPanel = view.getHrPanel();

        hrPanel.getSearchField().addActionListener(e->{
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            String searchTerm = hrPanel.getSearchField().getText();
            if (hrPanel.getRadioButtonName().isSelected()){
                model.setSearchResultByName(searchTerm);
            } else if (hrPanel.getRadioButtonID().isSelected()) {
                try{
                    model.setSearchResultById(Long.parseLong(searchTerm));
                }
                catch (NumberFormatException ex){
                    System.out.println("Exception was thrown; program continues");
                }
            }
        });

        hrPanel.getFilterComboBox().addActionListener(e -> {
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            String selectedPosition = (String) hrPanel.getFilterComboBox().getSelectedItem();
            assert selectedPosition != null;
            model.filterSearchResultByPosition(selectedPosition);
            updateFilteredResult();
        });

        hrPanel.getShowDetailsButton().addActionListener(e -> {
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            int selectedRow = hrPanel.getSearchResultTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(hrPanel, "Select an employee to view details");
                return;
            }
            try{
                long id = Long.parseLong(hrPanel.getSearchResultTable().getValueAt(selectedRow, 0).toString());
                model.setSelectedEmployee(id);
                updateEmployeeDetails();
            }
            catch(NumberFormatException ex){
                System.out.println("Exception was thrown; program continues");
            }
        });

        hrPanel.getMakeChangesButton().addActionListener(e->{
            if (appUser instanceof AuthorizedManager){
                if(!makingChanges){
                    makingChanges = true;
                    hrPanel.changeEmployeeState(true);
                }
                else{
                    System.out.println("Authorized manager trying to commit changes");
                    List<EmployeeInfoChange> changesMade = hrPanel.getChangesMade(model.getSelectedEmployee());
                    ((AuthorizedManager) appUser).makeUpdate(model.getSelectedEmployee(), changesMade);
                    model.setChangedEmployee(model.getSelectedEmployee());
                    updateEmployee();
                    hrPanel.getUndoChangesButton().setEnabled(true);
                }
            }
            else{
                JOptionPane.showMessageDialog(hrPanel, "You are not authorized to make changes");
            }
        });

        hrPanel.getUndoChangesButton().addActionListener(e->{
            if (appUser instanceof AuthorizedManager){
                try{
                    ((AuthorizedManager) appUser).undoCommand();
                    updateEmployee();
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(hrPanel, ex.getMessage());
                    hrPanel.getUndoChangesButton().setEnabled(false);
                }
            }
        });

        hrPanel.getNewEmployeeButton().addActionListener(e->{
            if(appUser instanceof AuthorizedManager){
                view.switchTo("Register");
            }
            else{
                JOptionPane.showMessageDialog(hrPanel, "You are not authorized to register new employees");
            }
        });

        hrPanel.getLogOutButton().addActionListener(e->{
            view.switchTo("Login");
        });
    }

    public void addListenersToRegisterPanel(){
        RegisterEmployeePanel registerPanel = view.getRegisterEmployeePanel();

        registerPanel.getDoneButton().addActionListener(e->{
            if (appUser instanceof AuthorizedManager authorizedManager){

                String name = registerPanel.getEmployeeName().getText();
                long id = Long.parseLong(registerPanel.getId().getText().trim());
                double salary = Double.parseDouble(registerPanel.getSalary().getText());
                int employmentPercentage = Integer.parseInt(registerPanel.getEmploymentPercentage().getText());
                String email = registerPanel.getEmail().getText();
                String phoneNumber = registerPanel.getPhoneNumber().getText();
                String positionName = (String) registerPanel.getPositionBox().getSelectedItem();

                Position selectedPosition = null;
                for(Position position : Position.values()){
                    if(Objects.requireNonNull(positionName).equalsIgnoreCase(position.title)){
                        selectedPosition = position;
                    }
                }

                authorizedManager.createNewEmployee(name, id, selectedPosition, salary, employmentPercentage, email, phoneNumber);
                populateAllEmployees();
                view.switchTo("HR");
            }
        });

        registerPanel.getCancelButton().addActionListener(e->{
            view.switchTo("HR");
        });
    }

    private void populateAllEmployees() {
        model.setSearchResultByName("");
    }

    @Override
    public void updateEmployee() {
        view.getHrPanel().resetTable();
        List<Employee> currentSearchResult = model.getCurrentSearchResult();
        for(Employee employee : currentSearchResult){
            view.getHrPanel().addEmployeeRow(employee);
        }
        view.setFieldsWithDetails(model.getChangedEmployee());
    }

    @Override
    public void updateEmployeeDetails() {
        view.setFieldsWithDetails(model.getSelectedEmployee());
    }

    @Override
    public void updateFilteredResult() {
        view.getHrPanel().resetTable();
        List<Employee> currentFilteredResult = model.getCurrentFilteredResult();
        for(Employee employee : currentFilteredResult){
            view.getHrPanel().addEmployeeRow(employee);
        }
    }

    @Override
    public void updateSearchResult() {
        view.getHrPanel().resetTable();
        List<Employee> currentSearchResult = model.getCurrentSearchResult();
        for(Employee employee: currentSearchResult){
            view.getHrPanel().addEmployeeRow(employee);
        }
    }
}
