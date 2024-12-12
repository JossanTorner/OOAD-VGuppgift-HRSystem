package ObserverView;

import javax.swing.*;
import java.awt.*;

public class RegisterEmployeePanel extends JPanel {

    JTextField employeeName;
    JTextField id;
    JTextField position;
    JTextField salary;
    JTextField employmentPercentage;
    JTextField phoneNumber;
    JTextField email;

    JComboBox<String> positionBox;

    JButton doneButton;
    JButton cancelButton;


    public RegisterEmployeePanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel headPanel1 = new JPanel(new GridLayout(1, 1));
        headPanel1.add(new JLabel("  EMPLOYEE INFORMATION", JLabel.LEFT));
        headPanel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel headPanel2 = new JPanel(new GridLayout(1, 1));
        headPanel2.add(new JLabel("  CONTACT DETAILS", JLabel.LEFT));
        headPanel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        positionBox = new JComboBox<>(new String[]{"CEO", "Manager", "Developer", "Product Owner", "Scrum-master", "Subordinate"});

        employeeName = new JTextField(15);
        id = new JTextField(15);
        salary = new JTextField(15);
        employmentPercentage = new JTextField(15);
        phoneNumber = new JTextField(15);
        email = new JTextField(15);

        this.add(headPanel1);
        this.add(createLabeledField("Name", employeeName));
        this.add(createLabeledField("ID", id));

        JPanel positionPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel position = new JLabel("Position", JLabel.LEFT);
        positionPanel.add(position);
        positionPanel.add(positionBox);
        this.add(positionPanel);

        this.add(createLabeledField("Salary", salary));
        this.add(createLabeledField("EmploymentPercentage", employmentPercentage));
        this.add(headPanel2);
        this.add(createLabeledField("PhoneNumber", phoneNumber));
        this.add(createLabeledField("Email", email));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        doneButton = new JButton("DONE");
        cancelButton = new JButton("CANCEL");
        buttonPanel.add(doneButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel);

    }

    private static JPanel createLabeledField(String labelText, JTextField field) {
        JPanel fieldPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // GridLayout with two columns
        JLabel label = new JLabel(labelText, JLabel.LEFT); // Right-align the labels
        fieldPanel.add(label);
        fieldPanel.add(field);
        return fieldPanel;
    }

    public JTextField getEmployeeName() {
        return employeeName;
    }

    public JTextField getId() {
        return id;
    }


    public JTextField getSalary() {
        return salary;
    }

    public JTextField getEmploymentPercentage() {
        return employmentPercentage;
    }

    public JTextField getPhoneNumber() {
        return phoneNumber;
    }

    public JTextField getEmail() {
        return email;
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JComboBox<String> getPositionBox() {
        return positionBox;
    }

}
