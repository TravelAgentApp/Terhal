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


//
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TravelAppGUI extends JFrame {
    private JTextField budgetField;
    private JTextField membersField;
    private JTextField travelPurposeField;
    private JTextField weatherField;
    private JTextField travelDurationField;

    public TravelAppGUI() {
        setTitle("Travel Application");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        getContentPane().setBackground(Color.decode("#E6E6FA")); // Set background color
        initComponents(); // Initialize components
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
        mainPanel.setBackground(Color.decode("#E6E6FA")); // Background color for panel

        mainPanel.add(new JLabel("Budget:"));
        budgetField = new JTextField();
        mainPanel.add(budgetField);

        mainPanel.add(new JLabel("Members:"));
        membersField = new JTextField();
        mainPanel.add(membersField);

        mainPanel.add(new JLabel("Travel Purpose:"));
        travelPurposeField = new JTextField();
        mainPanel.add(travelPurposeField);

        mainPanel.add(new JLabel("Weather:"));
        weatherField = new JTextField();
        mainPanel.add(weatherField);

        mainPanel.add(new JLabel("Travel Duration:"));
        travelDurationField = new JTextField();
        mainPanel.add(travelDurationField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.decode("#4CAF50")); // Button color
        submitButton.setForeground(Color.WHITE); // Button text color
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the submission of the main application form
                JOptionPane.showMessageDialog(null, "Data submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainPanel.add(submitButton);

        // Adding some padding to the panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(mainPanel); // Add main panel to frame
    }
}
