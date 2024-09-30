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
    private JTextField nameField;
    private JTextField emailField;
    private JRadioButton loginButton;
    private JRadioButton registerButton;

    private Connection conn; // Use the existing connection from NetBeans

    public TravelAppGUI(Connection connection) {
        this.conn = connection; // Assign the provided connection from your NetBeans setup

        setTitle("Travel App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        
       
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        // Radio buttons for login or register
        loginButton = new JRadioButton("Login");
        registerButton = new JRadioButton("Register");

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitActionListener());
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(new JLabel()); // Empty cell
        panel.add(submitButton);

        add(panel);
    }

    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();

            if (loginButton.isSelected()) {
                // Handle login logic
                handleLogin(name, email);
                displayAvailableCountries();
            } else if (registerButton.isSelected()) {
                // Handle register logic
                handleRegister(name, email);
                displayAvailableCountries();
            } else {
                JOptionPane.showMessageDialog(null, "Please select either Login or Register.");
            }
        }
    }

    // Handle user login
    private void handleLogin(String name, String email) {
        String query = "SELECT * FROM Users WHERE name = ? AND email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Successful login
                JOptionPane.showMessageDialog(null, "Login successful!");
                currentUser = new User();
                currentUser.setUserId(rs.getString("userId"));
                currentUser.setName(rs.getString("name"));
                currentUser.setEmail(rs.getString("email"));
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(null, "Invalid name or email.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Handle user registration
    private void handleRegister(String name, String email) {
        // First check if the email already exists
        String checkQuery = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // Email already exists
                JOptionPane.showMessageDialog(null, "Email already registered.");
            } else {
                // Register the new user
                registerNewUser(name, email);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Register a new user
    private void registerNewUser(String name, String email) {
        String query = "INSERT INTO Users (userId, name, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String userId = UUID.randomUUID().toString(); // Generate unique userId

            pstmt.setString(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registration successful!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void displayAvailableCountries() {
    Countries country = new Countries(conn); // Create Country object with DB connection
    double budgetforflight = 100; // For example, 80% of the trip's budget
    String weatherPreference = "cold"; // Example weather preference
    String activityPreference = "Hiking"; // Example activity preference

    // Get the list of countries
    List<String> availableCountries = country.selectCountry(budgetforflight, weatherPreference, activityPreference);

    // Display the countries
    if (!availableCountries.isEmpty()) {
        for (String countryName : availableCountries) {
            System.out.println("Available Country: " + countryName);
        }
    } else {
        System.out.println("No countries available for the selected preferences.");
    }
}
}

    
