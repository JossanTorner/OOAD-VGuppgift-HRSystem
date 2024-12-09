package Controller;

import EmployeeDatabase.Position;
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

        logInPanel.getCancelButton().addActionListener(e->{
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
                        view.updateFilteredResult();
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
    }

}
