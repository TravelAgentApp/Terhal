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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.Map;

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
        //showMainInfo();         // Call the main app window
        showPlansPage();
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
        showMainInfo();
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
    
    void showPlansPage(){
         // Launch the questionnaire to gather user preferences
        Questionaire Questionaire = new Questionaire(conn,currentUser.getUserId() );
        //call travel plan and take the userid as the constructor
        TravelPlan TravelPlan = new TravelPlan(Questionaire.getcityNames(currentUser.getUserId()),currentUser.getUserId(),Questionaire.getTripIdByUserId(currentUser.getUserId()), conn);
        App DisplayHome = new App (TravelPlan, Questionaire.getTripIdByUserId(currentUser.getUserId()));
        //TravelPlanner travelPlanner = new TravelPlanner(TravelPlan);
    }

public class App extends JFrame {
    private JPanel cityPanel;
    private JFrame mainFrame;
    private Map<String, DailyPlan> dailyPlans;
    TravelPlan travelPlan;
    int tripId;

    public App(TravelPlan travelPlan, int tripId) {
        this.travelPlan = travelPlan;
        this.tripId = tripId;
        this.dailyPlans = new HashMap<>();
        setTitle("Welcome to Terhal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Program icon
        setIconImage(Toolkit.getDefaultToolkit().getImage("path/to/icon.png"));

        // Panel to display all city images
        cityPanel = new JPanel();
        cityPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Display in a vertical list
        cityPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Use city names from the TravelPlan object
        String[] cities = travelPlan.getCitynames();
        for (String city : cities) {
            cityPanel.add(createCityPanel(city));
        }

        JScrollPane scrollPane = new JScrollPane(cityPanel); // Scrollable display
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(135, 206, 235));

    // "Explore" button (non-functional for now)
    JButton exploreButton = new JButton("Explore");
    exploreButton.setBackground(new Color(0, 153, 51));
    exploreButton.setForeground(Color.WHITE);
    exploreButton.setFont(new Font("Arial", Font.BOLD, 16));

    // "Add New Plan" button, which will take the user back to showMainInfo() to add new preferences
    JButton addNewPlanButton = new JButton("Add New Plan");
    addNewPlanButton.setBackground(new Color(0, 102, 204));
    addNewPlanButton.setForeground(Color.WHITE);
    addNewPlanButton.setFont(new Font("Arial", Font.BOLD, 16));

    // Add action listener for "Add New Plan" button
    addNewPlanButton.addActionListener(e -> {
        showMainInfo(); // Call showMainInfo() to add new preferences
        
    });

    // Add buttons to the panel
    buttonPanel.add(exploreButton);
    buttonPanel.add(addNewPlanButton);

    // Add button panel to the bottom of the frame
    add(buttonPanel, BorderLayout.SOUTH);

    setVisible(true);
    }

    private JPanel createCityPanel(String cityName) {
        JPanel panel = new JPanel(new BorderLayout());
      
        // Create a clickable JLabel for the city name
        JLabel cityLabel = new JLabel(cityName, JLabel.CENTER);
        cityLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        cityLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open a new frame when the city name is clicked
                
               initializePlans();
               showWeeklyPlan();
            }
        });

        JLabel cityImageLabel = new JLabel("", JLabel.CENTER);
        
        String imagePath = getCityImagePath(cityName); // Method to get image path based on city
        ImageIcon cityIcon = resizeImage(imagePath, 300, 200);

        if (cityIcon != null) {
            cityImageLabel.setIcon(cityIcon);
        } else {
            cityImageLabel.setText("Image not available!");
        }

        panel.add(cityLabel, BorderLayout.NORTH);
        panel.add(cityImageLabel, BorderLayout.CENTER);
        return panel;
    }


   private String getCityImagePath(String cityName) {
    switch (cityName) {
        case "Abha": 
            return "src/Abha.jpg";
        case "Dammam": 
            return "src/Dammam.jpg";
        case "Hafar Al-Batin": 
            return "src/HafarAlBatin.jpg";
        case "Jeddah": 
            return "src/Jeddah.jpg";
        case "Khamis Mushait": 
            return "src/KhamisMushait.jpg";
        case "Khobar": 
            return "src/Khobar.jpg";
        case "Medina": 
            return "src/Medina.jpg";
        case "Najran": 
            return "src/Najran.jpg";
        case "Riyadh": 
            return "src/Riyadh.jpg";
        case "Tabuk": 
            return "src/Tabuk.jpg";
        case "Makkah": 
            return "src/Makkah.jpg";
        default: 
            return null;
    }
}


    // Resize image
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getIconWidth() == -1) return null;
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
    
    
       // Initialize plans based on data from TravelPlan
    public void initializePlans() {
        // Assuming a fixed duration of 5 days; you could modify this to be dynamic
        int travelDuration = travelPlan.getduration(tripId, currentUser.getUserId() );
            
        
        // Retrieve activities and restaurants based on city and preferences
        for (String city : travelPlan.getCitynames()) {
            // Retrieve activities for the city
            Map<String, List<Integer>> activitiesByTime = travelPlan.getActivitiesByTime(city, travelPlan.getactivityPrefference(tripId,currentUser.getUserId()));
            
            
            // Retrieve restaurants for the city
            List<Integer> restaurants = travelPlan.getRestaurantsByPreference(city, travelPlan.getCuisinePrefference(tripId,currentUser.getUserId() ));
             // Debugging output
        System.out.println("City: " + city);
        System.out.println("Activities by time: " + activitiesByTime);
        System.out.println("Restaurants: " + restaurants);
            // Create daily itinerary using fetched data
            List<Day> itinerary = travelPlan.createDailyItinerary(activitiesByTime, restaurants, travelDuration);
            
            // Populate DailyPlan instances for each day
            for (Day day : itinerary) {
                String dayName = day.getDayName();
                DailyPlan dailyPlan = new DailyPlan(dayName, city, 
                                                    "Activity ID: " + day.getMorningActivity(),
                                                    "Restaurant ID: " + day.getRestaurantBreakfast(), 
                                                    "Weather data here", mainFrame);
                dailyPlans.put(dayName, dailyPlan);
            }
        }
    }

    public void showWeeklyPlan() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        
        mainFrame = new JFrame("Weekly Travel Plan");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new BorderLayout());

        JPanel planPanel = new JPanel(new GridLayout(0, 1));
        for (String day : dailyPlans.keySet()) {
            JButton dayButton = new JButton(day);
            dayButton.addActionListener(e -> showDailyPlan(day));
            planPanel.add(dayButton);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainFrame.dispose());
        buttonPanel.add(backButton);

        mainFrame.add(planPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private void showDailyPlan(String day) {
        DailyPlan dailyPlan = dailyPlans.get(day);
        if (dailyPlan != null) {
            dailyPlan.showDetails();
        }
    }

    // Inner DailyPlan class remains the same
    static class DailyPlan {
        private String day;
        private String city;
        private String activities;
        private String restaurants;
        private String weather;
        private JFrame mainFrame;
        private JFrame parentFrame;

        public DailyPlan(String day, String city, String activities, String restaurants, String weather, JFrame parentFrame) {
            this.day = day;
            this.city = city;
            this.activities = activities;
            this.restaurants = restaurants;
            this.weather = weather;
            this.parentFrame = parentFrame;
        }

        public void showDetails() {
            if (mainFrame != null) {
                mainFrame.dispose();
            }
            mainFrame = new JFrame(day + " Plan in " + city);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setSize(400, 300);
            mainFrame.setLayout(new BorderLayout());

            JPanel detailsPanel = new JPanel(new GridLayout(4, 1));
            detailsPanel.add(new JLabel("Activities: " + activities));
            detailsPanel.add(new JLabel("Restaurants: " + restaurants));
            detailsPanel.add(new JLabel("Weather: " + weather));

            JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
            JButton editButton = new JButton("Edit Plan");
            JButton saveButton = new JButton("Save Plan");
            JButton backButton = new JButton("Back");

            editButton.addActionListener(e -> showEditDialog());
            saveButton.addActionListener(e -> savePlanToFile());
            backButton.addActionListener(e -> {
                mainFrame.dispose();
                parentFrame.setVisible(true);
            });

            buttonPanel.add(editButton);
            buttonPanel.add(saveButton);
            buttonPanel.add(backButton);

            mainFrame.add(detailsPanel, BorderLayout.CENTER);
            mainFrame.add(buttonPanel, BorderLayout.SOUTH);

            mainFrame.setVisible(true);
        }

        // Method to show edit dialog, save plan to file, etc. remain unchanged
        
         private void showEditDialog() {
            JFrame editFrame = new JFrame("Edit " + day + " Plan");
            editFrame.setSize(400, 300);
            editFrame.setLayout(new GridLayout(5, 2));

            JTextField activitiesField = new JTextField(activities);
            JTextField restaurantsField = new JTextField(restaurants);
            JTextField weatherField = new JTextField(weather);

            editFrame.add(new JLabel("Activities:"));
            editFrame.add(activitiesField);
            editFrame.add(new JLabel("Restaurants:"));
            editFrame.add(restaurantsField);
            editFrame.add(new JLabel("Weather:"));
            editFrame.add(weatherField);

            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(e -> {
                if (!activitiesField.getText().isEmpty() && !restaurantsField.getText().isEmpty() && !weatherField.getText().isEmpty()) {
                    activities = activitiesField.getText();
                    restaurants = restaurantsField.getText();
                    weather = weatherField.getText();
                    
                    editFrame.dispose();
                    showDetails(); // Refresh the details view
                } else {
                    JOptionPane.showMessageDialog(editFrame, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            });

            editFrame.add(saveButton);
            editFrame.setVisible(true);
        }

        private void savePlanToFile() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(mainFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    writer.write(toString());
                    writer.newLine();
                    JOptionPane.showMessageDialog(mainFrame, "Plan saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, "Error saving the plan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

}




}
}
    
