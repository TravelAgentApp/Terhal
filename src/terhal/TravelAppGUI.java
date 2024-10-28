/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package terhal;

/**
 *
 * @author janaz
 */
//package com.mycompany.travelappgui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.List;

public class TravelAppGUI extends JFrame {
    private User currentUser;
    private Connection conn;  // Database connection
    private JTextField firstNameField;
    private JTextField secondNameField;
    private JTextField lastNameField;
    private JTextField userField;
    private JTextField emailField;
    private JComboBox<String> domainComboBox;
    private JPasswordField passwordField;

    //connect to database
    public TravelAppGUI(Connection connection) {
        this.conn = connection; // Assign the provided connection from the Terhal2 class

        setTitle("Terhal Travel App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showLoginOrRegister(); // Show login or register options
    }

// Show the login or register options
void showLoginOrRegister() {
    JFrame frame = new JFrame("Login or Register");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(400, 250);
    frame.setLayout(new GridLayout(2, 1));

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    loginButton.setFont(new Font("Arial", Font.BOLD, 18));
    registerButton.setFont(new Font("Arial", Font.BOLD, 18));

    loginButton.addActionListener(e -> {
        frame.dispose(); // Dispose of the login/register window when moving to login
        showLogin(frame); // Pass frame to showLogin for further handling
    });

    registerButton.addActionListener(e -> {
        frame.dispose(); // Dispose of the login/register window when moving to registration
        showRegister(frame); // Pass frame to showRegister for further handling
    });

    frame.add(loginButton);
    frame.add(registerButton);
    frame.setVisible(true);
}

private void showLogin(JFrame mainFrame) {
    JFrame loginFrame = new JFrame("Login");
    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    loginFrame.setSize(400, 300);
    loginFrame.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField(15);
    //JLabel emailLabel = new JLabel("Email:");
    //JTextField emailField = new JTextField(15);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(15);
    JButton submitButton = new JButton("Submit");

    gbc.gridx = 0;
    gbc.gridy = 0;
    loginFrame.add(userLabel, gbc);
    gbc.gridx = 1;
    loginFrame.add(userField, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    //loginFrame.add(emailLabel, gbc);
    gbc.gridx = 1;
    //loginFrame.add(emailField, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    loginFrame.add(passwordLabel, gbc);
    gbc.gridx = 1;
    loginFrame.add(passwordField, gbc);
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    loginFrame.add(submitButton, gbc);

    submitButton.addActionListener(e -> handleLogin(loginFrame, userField.getText(), new String(passwordField.getPassword()), mainFrame));

    loginFrame.setVisible(true);
}

private void handleLogin(JFrame loginFrame, String username, String password, JFrame mainFrame) {
    if (validateLogin(username, password)) {
        currentUser = new User(); // Create the User object and set the properties as needed
        currentUser.setUsername(username);// Set username or any other relevant info
        String query = "SELECT userId FROM Users WHERE username = ? AND password_hash = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
             ResultSet rs = pstmt.executeQuery();
             rs.next();
            currentUser.setUserId(rs.getString("userId") );
        
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        loginFrame.dispose();  // Close login window
        mainFrame.dispose();   // Close the login/register window (passed as an argument)
        showMainInfo();         // Call the main app window
    } else {
        JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials.");
    }
}

    private void showRegister(JFrame frame) {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(400, 400);
        registerFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(15);
        JLabel secondNameLabel = new JLabel("Second Name:");
        secondNameField = new JTextField(15);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(15);
        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField(15);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        JLabel domainLabel = new JLabel("Domain:");
        String[] domains = {"@gmail.com", "@hotmail.com"};
        domainComboBox = new JComboBox<>(domains);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        JButton submitButton = new JButton("Submit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        registerFrame.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(firstNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        registerFrame.add(secondNameLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(secondNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerFrame.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(lastNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerFrame.add(userLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        registerFrame.add(emailLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        registerFrame.add(domainLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(domainComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        registerFrame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        registerFrame.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        registerFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> handleRegister(registerFrame));

        registerFrame.setVisible(true);
    }

    private void handleRegister(JFrame frame) {
        String firstName = firstNameField.getText().trim();
        String secondName = secondNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = userField.getText().trim();
        String email = emailField.getText().trim() + domainComboBox.getSelectedItem();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || secondName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showErrorMessage(frame, "Please fill in all fields.");
            return;
        }

        if (!firstName.matches("[a-zA-Z]+") || !secondName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            showErrorMessage(frame, "Names should contain only alphabetic characters.");
            return;
        }

        if (isEmailRegistered(email)) {
            showErrorMessage(frame, "Email already registered.");
            return;
        }

        if (!username.matches("[a-zA-Z]+")) {
            showErrorMessage(frame, "Username should contain only alphabetic characters.");
            return;
        }

        if (password.length() != 8) {
            showErrorMessage(frame, "Password must be exactly 8 characters long.");
            return;
        }

        registerNewUser(firstName + " " + secondName + " " + lastName, email, username, password);
        JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    private boolean isEmailRegistered(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If a record exists, the email is already registered
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register the new user in the database
    private void registerNewUser(String fullName, String email, String username, String password) {
        String query = "INSERT INTO Users (userId, name, email, username, password_hash) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, UUID.randomUUID().toString()); // Generate unique userId
            pstmt.setString(2, fullName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password); // Ideally hash the password
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate login credentials from the database
    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If a record exists, the credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   private void showErrorMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    void showMainInfo() {
        // Launch the questionnaire to gather user preferences
        Questionaire Questionaire = new Questionaire(conn,currentUser.getUserId() );
        Questionaire.showMainInfo();
        //call travel plan and take the userid as the constructor
    }
    
}
    
