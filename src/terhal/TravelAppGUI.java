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
import java.sql.Statement;
import java.util.UUID;

public class TravelAppGUI extends JFrame {
    private User currentUser;
    private Questionaire questionaire;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField preferencesField;
    private JTextField budgetField;

    private Connection conn; // Use the existing connection from NetBeans

    public TravelAppGUI(Connection connection) {
        this.conn = connection; // Assign the provided connection from your NetBeans setup

        setTitle("Travel App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load user data if it exists in the database
        loadUserData();

        initComponents();
    }
//
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(currentUser != null ? currentUser.getName() : "");
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(currentUser != null ? currentUser.getEmail() : "");
        JLabel preferencesLabel = new JLabel("Preferences:");
        preferencesField = new JTextField(currentUser != null ? currentUser.getPreferences() : "");
        JLabel budgetLabel = new JLabel("Budget:");
        budgetField = new JTextField(currentUser != null ? Float.toString(currentUser.getBudget()) : "");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitActionListener());

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(preferencesLabel);
        panel.add(preferencesField);
        panel.add(budgetLabel);
        panel.add(budgetField);
        panel.add(new JLabel()); // Empty cell
        panel.add(submitButton);

        add(panel);
    }

    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new user object and set the data from GUI
            currentUser = new User();
            currentUser.setName(nameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setPreferences(preferencesField.getText());
            currentUser.setBudget(Float.parseFloat(budgetField.getText()));

            // Optionally, create a questionnaire for the user
            questionaire = new Questionaire();
            questionaire.setUser(currentUser);

            // Save user data to MySQL database
            saveUserData();

            JOptionPane.showMessageDialog(null, "User information saved!");
        }
    }

    // Save the user data to MySQL database
    private void saveUserData() {
        String query = "REPLACE INTO users (userId, name, email, preferences, budget) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (currentUser.getUserId() == null || currentUser.getUserId().isEmpty()) {
                currentUser.setUserId(UUID.randomUUID().toString()); // Generate unique ID if not available
            }

            pstmt.setString(1, currentUser.getUserId());
            pstmt.setString(2, currentUser.getName());
            pstmt.setString(3, currentUser.getEmail());
            pstmt.setString(4, currentUser.getPreferences());
            pstmt.setFloat(5, currentUser.getBudget());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load the user data from the MySQL database (if it exists)
    private void loadUserData() {
        String query = "SELECT userId, name, email, preferences, budget FROM users LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                currentUser = new User();
                currentUser.setUserId(rs.getString("userId"));
                currentUser.setName(rs.getString("name"));
                currentUser.setEmail(rs.getString("email"));
                currentUser.setPreferences(rs.getString("preferences"));
                currentUser.setBudget(rs.getFloat("budget"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    
