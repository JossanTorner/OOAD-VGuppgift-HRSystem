package Controller;

import EmployeeDatabase.Employee;
import EmployeeDatabase.Position;
import HR.Commands.EmployeeInfoChange;
import HR.UserSystem.AppUser;
import HR.UserSystem.HRDatabase;
import HR.UserSystem.AuthorizedManager;
import ObserverView.HRPanel;
import ObserverView.LogInPanel;
import SubjectModel.HRModel;
import ObserverView.HRView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HRController {

    private final HRView view;
    private final HRModel model;
    private final HRDatabase hrDatabase;
    private AppUser appUser;
    private boolean makingChanges = false;

    public HRController(HRModel model) {
        this.model = model;
        view = new HRView(model);
        view.init();
        hrDatabase = new HRDatabase();
        addActionListenersToHRPanel();
        addActionListenersToLoginPanel();
    }

    public void addActionListenersToLoginPanel(){
        LogInPanel logInPanel = view.getLogInPanel();

        logInPanel.getCancelButton().addActionListener(e->{
            System.exit(0);
        });

        logInPanel.getLoginButton().addActionListener(e->{

            String name = logInPanel.getUserField().getText();
            System.out.println(name);
            String password = new String(logInPanel.getPasswordField().getPassword());
            System.out.println(password);

            AppUser loggedIn = hrDatabase.findUserInDatabase(name, password);
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

    public void addActionListenersToHRPanel(){
        HRPanel hrPanel = view.getHrPanel();

        hrPanel.getSearchField().addActionListener(e->{
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            String searchTerm = hrPanel.getSearchField().getText();
            if (hrPanel.getRadioButtonName().isSelected()){
                model.setSearchResultByName(searchTerm);
            } else if (hrPanel.getRadioButtonID().isSelected()) {
                model.setSearchResultById(Long.parseLong(searchTerm));
            }
        });

        hrPanel.getFilterComboBox().addActionListener(e -> {
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            String selectedPosition = (String) hrPanel.getFilterComboBox().getSelectedItem();
            if (!selectedPosition.equalsIgnoreCase("none")) {
                for (Position position : Position.values()) {
                    if (position.title.equalsIgnoreCase(selectedPosition)) {
                        model.filterSearchResultByPosition(position);
                        view.updateFilterSearch();
                    }
                }
            }
        });

        hrPanel.getShowDetailsButton().addActionListener(e -> {
            makingChanges = false;
            hrPanel.changeEmployeeState(false);
            int selectedRow = hrPanel.getSearchResultTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(hrPanel, "Select an employee to view details");
                return;
            }
            long id = Long.parseLong(hrPanel.getSearchResultTable().getValueAt(selectedRow, 0).toString());
            model.setSelectedEmployee(id);
            view.updateEmployeeDetails();
        });

        hrPanel.getMakeChangesButton().addActionListener(e->{
            if (appUser instanceof AuthorizedManager){
                if(!makingChanges){
                    makingChanges = true;
                    hrPanel.changeEmployeeState(true);
                }
                else{
                    System.out.println("Authorized manager trying to commit changes");
                    List<EmployeeInfoChange> changesMade = getChangesMade(model.getSelectedEmployee());
                    ((AuthorizedManager) appUser).makeUpdate(model.getSelectedEmployee(), changesMade);
                    model.setChangedEmployee(model.getSelectedEmployee());
                    view.updateEmployee();
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
                    ((AuthorizedManager) appUser).undoUpdate();
                    view.updateEmployee();
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(hrPanel, ex.getMessage());
                    hrPanel.getUndoChangesButton().setEnabled(false);
                }
            }
        });
    }

    public List<EmployeeInfoChange> getChangesMade(Employee selectedEmployee){
        List<EmployeeInfoChange> changes = new ArrayList<>();

        HRPanel panelWithDetails = view.getHrPanel();
        String currentName = panelWithDetails.getShowDetailsNameTextField().getText();
        if (!currentName.equalsIgnoreCase(selectedEmployee.getName())) {
            EmployeeInfoChange change = new EmployeeInfoChange("NAME", selectedEmployee.getName(), currentName);
            changes.add(change);
        }
        double currentSalary = Double.parseDouble(panelWithDetails.getShowDetailsSalaryTextField().getText());
        if (!(currentSalary == selectedEmployee.getSalary())){
            EmployeeInfoChange change = new EmployeeInfoChange("SALARY", selectedEmployee.getSalary(), currentSalary);
            changes.add(change);
        }
        Position position = Position.valueOf(panelWithDetails.getShowDetailsPositionTextField().getText());
        if(position != selectedEmployee.getPosition()){
            EmployeeInfoChange change = new EmployeeInfoChange("POSITION", selectedEmployee.getPosition(), position);
            changes.add(change);
        }
        String currentPhoneNumber = panelWithDetails.getShowDetailsPhoneTextField().getText();
        if (!currentPhoneNumber.equalsIgnoreCase(selectedEmployee.getPhoneNumber())) {
            EmployeeInfoChange change = new EmployeeInfoChange("PHONE_NUMBER", selectedEmployee.getPhoneNumber(), currentPhoneNumber);
            changes.add(change);
        }
        String currentEmail = panelWithDetails.getShowDetailsEmailTextField().getText();
        if (!currentEmail.equalsIgnoreCase(selectedEmployee.getEmail())) {
            EmployeeInfoChange change = new EmployeeInfoChange("EMAIL", selectedEmployee.getEmail(), currentEmail);
            changes.add(change);
        }
        int currentWorkingPercentage = Integer.parseInt(panelWithDetails.getShowDetailsEmploymentPercentageTextField().getText());
        if(currentWorkingPercentage != selectedEmployee.getWorkingPercentage()){
            EmployeeInfoChange change = new EmployeeInfoChange("WORKING_PERCENTAGE", selectedEmployee.getWorkingPercentage(), currentWorkingPercentage);
            changes.add(change);
        }
        return changes;
    }

}
