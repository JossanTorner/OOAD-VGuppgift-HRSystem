package ObserverView;

import EmployeeDatabase.Employee;
import EmployeeDatabase.Position;
import HR.Commands.EmployeeInfoChange;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HRPanel extends JPanel {

    JPanel topPanel;
    JLabel searchLabel;
    JTextField searchField;
    JPanel radioButtonPanel;
    ButtonGroup buttonGroup;
    JRadioButton radioButtonName;
    JRadioButton radioButtonID;
    JLabel filterLabel;
    JComboBox<String> filterComboBox;

    JButton logOutButton;

    JPanel centerPanel;
    JPanel searchResultPanel;
    JTable searchResultTable;
    DefaultTableModel searchResultTableModel;
    JScrollPane searchResultScrollPane;

    JPanel showDetailsMainPanel;
    JPanel showDetailsTopPanel;
    JButton showDetailsButton;
    JPanel showDetailsCenterPanel;
    JTextField showDetailsNameTextField;
    JTextField showDetailsEmploymentPercentageTextField;
    JTextField showDetailsPositionTextField;
    JTextField showDetailsEmailTextField;
    JTextField showDetailsSalaryTextField;
    JTextField showDetailsPhoneTextField;

    JButton makeChangesButton;
    JButton undoChangesButton;

    public HRPanel() {
        topPanel = new JPanel();
        searchLabel = new JLabel("Search  ", SwingConstants.RIGHT);
        searchField = new JTextField();
        radioButtonPanel = new JPanel();
        buttonGroup  = new ButtonGroup();
        radioButtonName = new JRadioButton("Name");
        radioButtonID = new JRadioButton("ID");
        filterLabel = new JLabel("Filter      ", SwingConstants.RIGHT);
        filterComboBox = new JComboBox<>(new String[]{"None", "CEO", "Manager", "Developer", "Product Owner", "Scrum-master", "Subordinate"});

        logOutButton = new JButton("Log Out");

        centerPanel = new JPanel();
        searchResultPanel = new JPanel();
        searchResultTableModel = new DefaultTableModel();
        searchResultTable = new JTable(searchResultTableModel);
        searchResultTableModel.addColumn("Name");
        searchResultTableModel.addColumn("ID");
        searchResultTableModel.addColumn("Position");

        makeChangesButton = new JButton("Edit employee details");
        undoChangesButton = new JButton("Undo Changes");
        searchResultScrollPane = new JScrollPane(searchResultTable);
        showDetailsMainPanel = new JPanel();
        showDetailsTopPanel = new JPanel();
        showDetailsButton = new JButton("Show Details");
        showDetailsCenterPanel = new JPanel();
        showDetailsNameTextField = new JTextField("Jane Doe");
        showDetailsEmploymentPercentageTextField = new JTextField("100%");
        showDetailsPositionTextField = new JTextField("Manager");
        showDetailsEmailTextField = new JTextField("jane@doe.com");
        showDetailsSalaryTextField = new JTextField("53 000");
        showDetailsPhoneTextField = new JTextField("09-12 55 12");
        init();
    }

    public void init(){
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        radioButtonName.setSelected(true);
        buttonGroup.add(radioButtonName);
        buttonGroup.add(radioButtonID);
        radioButtonPanel.setLayout(new GridLayout(2, 1));
        radioButtonPanel.add(radioButtonName);
        radioButtonPanel.add(radioButtonID);

        topPanel.setLayout(new GridLayout(1,5));
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(radioButtonPanel);
        topPanel.add(filterLabel);
        topPanel.add(filterComboBox);
        topPanel.add(logOutButton);

        centerPanel.setLayout(new GridLayout(2,1));
        centerPanel.add(searchResultPanel);
        centerPanel.add(showDetailsMainPanel);

        searchResultPanel.setLayout(new GridLayout(1,1));
        searchResultPanel.add(searchResultScrollPane);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        searchResultTable.setDefaultRenderer(Object.class, centerRenderer);


        showDetailsMainPanel.setLayout(new BorderLayout());
        showDetailsMainPanel.add(showDetailsTopPanel, BorderLayout.NORTH);
        showDetailsTopPanel.setLayout(new GridLayout(1,1));
        showDetailsTopPanel.add(showDetailsButton, BorderLayout.NORTH);

        showDetailsMainPanel.add(showDetailsCenterPanel);
        showDetailsCenterPanel.setLayout(new GridLayout(4,3));
        showDetailsCenterPanel.add(new JLabel("Name"));
        showDetailsCenterPanel.add(new JLabel("Employment Percentage"));
        showDetailsCenterPanel.add(new JLabel("Position"));
        showDetailsCenterPanel.add(showDetailsNameTextField);
        showDetailsCenterPanel.add(showDetailsEmploymentPercentageTextField);
        showDetailsCenterPanel.add(showDetailsPositionTextField);
        showDetailsCenterPanel.add(new JLabel("Email"));
        showDetailsCenterPanel.add(new JLabel("Salary"));
        showDetailsCenterPanel.add(new JLabel("Phone number"));
        showDetailsCenterPanel.add(showDetailsEmailTextField);
        showDetailsCenterPanel.add(showDetailsSalaryTextField);
        showDetailsCenterPanel.add(showDetailsPhoneTextField);
//        showDetailsMainPanel.add(makeChangesButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); // Two rows for two buttons
        buttonPanel.add(makeChangesButton);
        buttonPanel.add(undoChangesButton);
        showDetailsMainPanel.add(buttonPanel, BorderLayout.SOUTH);
        undoChangesButton.setEnabled(false);

        setDetailsFieldEditable(false);
    }

    public List<EmployeeInfoChange> getChangesMade(Employee selectedEmployee){
        List<EmployeeInfoChange> changes = new ArrayList<>();

        String currentName = showDetailsNameTextField.getText();
        if (!currentName.equalsIgnoreCase(selectedEmployee.getName())) {
            EmployeeInfoChange change = new EmployeeInfoChange("NAME", selectedEmployee.getName(), currentName);
            changes.add(change);
        }
        double currentSalary = Double.parseDouble(showDetailsSalaryTextField.getText());
        if (!(currentSalary == selectedEmployee.getSalary())){
            EmployeeInfoChange change = new EmployeeInfoChange("SALARY", selectedEmployee.getSalary(), currentSalary);
            changes.add(change);
        }
        Position position = Position.valueOf(showDetailsPositionTextField.getText());
        if(position != selectedEmployee.getPosition()){
            EmployeeInfoChange change = new EmployeeInfoChange("POSITION", selectedEmployee.getPosition(), position);
            changes.add(change);
        }
        String currentPhoneNumber = showDetailsPhoneTextField.getText();
        if (!currentPhoneNumber.equalsIgnoreCase(selectedEmployee.getPhoneNumber())) {
            EmployeeInfoChange change = new EmployeeInfoChange("PHONE_NUMBER", selectedEmployee.getPhoneNumber(), currentPhoneNumber);
            changes.add(change);
        }
        String currentEmail = showDetailsEmailTextField.getText();
        if (!currentEmail.equalsIgnoreCase(selectedEmployee.getEmail())) {
            EmployeeInfoChange change = new EmployeeInfoChange("EMAIL", selectedEmployee.getEmail(), currentEmail);
            changes.add(change);
        }
        int currentWorkingPercentage = Integer.parseInt(showDetailsEmploymentPercentageTextField.getText());
        if(currentWorkingPercentage != selectedEmployee.getWorkingPercentage()){
            EmployeeInfoChange change = new EmployeeInfoChange("WORKING_PERCENTAGE", selectedEmployee.getWorkingPercentage(), currentWorkingPercentage);
            changes.add(change);
        }
        return changes;
    }

    public void setDetailsFieldEditable(boolean isEditable) {
        showDetailsNameTextField.setEditable(isEditable);
        showDetailsEmploymentPercentageTextField.setEditable(isEditable);
        showDetailsPositionTextField.setEditable(isEditable);
        showDetailsEmailTextField.setEditable(isEditable);
        showDetailsSalaryTextField.setEditable(isEditable);
        showDetailsPhoneTextField.setEditable(isEditable);
    }

    public void changeEmployeeState(boolean makingChanges){
        if (makingChanges){
            setDetailsFieldEditable(true);
            makeChangesButton.setText("Commit change");
        }
        else{
            setDetailsFieldEditable(false);
            makeChangesButton.setText("Edit employee details");
        }
    }

    public void addEmployeeRow(Employee employee) {
        searchResultTableModel.addRow(new String[]{String.valueOf(employee.getEmployeeId()), employee.getName(), employee.getPosition().title});
    }

    public void resetTable(){
        searchResultTableModel.setRowCount(0);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JRadioButton getRadioButtonName() {
        return radioButtonName;
    }

    public JRadioButton getRadioButtonID() {
        return radioButtonID;
    }

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    public JButton getShowDetailsButton() {
        return showDetailsButton;
    }

    public JTable getSearchResultTable() {
        return searchResultTable;
    }

    public DefaultTableModel getSearchResultTableModel() {
        return searchResultTableModel;
    }

    public JTextField getShowDetailsNameTextField() {
        return showDetailsNameTextField;
    }

    public JTextField getShowDetailsEmploymentPercentageTextField() {
        return showDetailsEmploymentPercentageTextField;
    }

    public JTextField getShowDetailsPositionTextField() {
        return showDetailsPositionTextField;
    }

    public JTextField getShowDetailsEmailTextField() {
        return showDetailsEmailTextField;
    }

    public JTextField getShowDetailsSalaryTextField() {
        return showDetailsSalaryTextField;
    }

    public JTextField getShowDetailsPhoneTextField() {
        return showDetailsPhoneTextField;
    }

    public JButton getMakeChangesButton() {
        return makeChangesButton;
    }

    public JButton getUndoChangesButton(){
        return undoChangesButton;
    }

    public JButton getLogOutButton(){
        return logOutButton;
    }
}
