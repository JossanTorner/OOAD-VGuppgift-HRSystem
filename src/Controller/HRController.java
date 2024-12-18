package Controller;

import HR.Commands.EmployeeInfoChange;
import HR.UserSystem.AppUser;
import HR.UserSystem.UserDatabase;
import HR.UserSystem.AuthorizedManager;
import ObserverView.HRPanel;
import ObserverView.LogInPanel;
import SubjectModel.HRModel;
import ObserverView.HRView;

import javax.swing.*;
import java.util.List;

public class HRController {

    private final HRView view;
    private final HRModel model;
    private AppUser appUser;
    private boolean makingChanges = false;

    public HRController(HRModel model) {
        this.model = model;
        view = new HRView(model);
        view.init();
        addListenersToHRPanel();
        addListenersToLoginPanel();
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
                view.populateAllEmployees();
                view.switchTo("HR");
            }
            else{
                JOptionPane.showMessageDialog(view.getLogInPanel(), "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                logInPanel.getPasswordField().setText("");
                logInPanel.getUserField().setText("");
            }
            logInPanel.resetLoginPanel();
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
            view.updateFilteredResult();
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
                view.updateEmployeeDetails();
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

        hrPanel.getLogOutButton().addActionListener(e->{
            appUser = null;
            view.switchTo("Login");
        });
    }

}
