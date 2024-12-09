package ObserverView;

import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JPanel {

    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();

    JButton loginButton = new JButton("Login");
    JButton exitButton = new JButton("Exit");

    public LogInPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fieldsPanel.add(userLabel);
        fieldsPanel.add(userField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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

    public JButton getExitButton() {
        return exitButton;
    }


}
