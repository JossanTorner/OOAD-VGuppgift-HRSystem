package ObserverView;

import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JPanel {

    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();

    JButton loginButton = new JButton("Login");
    JButton cancelButton = new JButton("Cancel");

    public LogInPanel() {

        setLayout(new GridLayout(3, 2, 10, 10));

        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(cancelButton);
    }

    public JTextField getUserField() {
        return userField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }


}
