
package terhal;

import java.util.UUID;

/**
 *
 * @author s4ooo
 */

//
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class User extends JFrame {
    private String userId;
    private String name;
    private String email;
    private JPasswordField passwordField;

    public User() {
        this.userId = UUID.randomUUID().toString();
        initLoginComponents(); // Initialize login components
    }

    private void initLoginComponents() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        getContentPane().setBackground(Color.decode("#F0F8FF"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.decode("#F0F8FF"));

        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(); // Use JTextField for email input
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.decode("#4CAF50")); // Button color
        loginButton.setForeground(Color.WHITE); // Button text color
        loginButton.addActionListener(new LoginActionListener(nameField, emailField)); // Pass emailField to listener
        panel.add(loginButton);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(panel);
    }

    private class LoginActionListener implements ActionListener {
        private JTextField nameField;
        private JTextField emailField;

        public LoginActionListener(JTextField nameField, JTextField emailField) {
            this.nameField = nameField;
            this.emailField = emailField; // Initialize emailField
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            name = nameField.getText();
            email = emailField.getText(); // Get text from emailField
            String password = new String(passwordField.getPassword());

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                TravelAppGUI app = new TravelAppGUI(); // Create instance of TravelAppGUI
                app.setVisible(true); // Show main application
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Getters and Setters for User attributes
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User userApp = new User();
            userApp.setVisible(true);
        });
    }
}
