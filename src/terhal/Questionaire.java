
package terhal;



import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Required for Statement.RETURN_GENERATED_KEYS



public class Questionaire {

    static String getaActivityPreference() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    // Class attributes to store user preferences
    int tripID;
    private int tripId; // Primary key
    private String userId; // Foreign key referencing User
    private String purpose; // Purpose of the trip
    private LocalDate durationStart; // Start date of the trip
    private LocalDate durationEnd; // End date of the trip
    private double minBudget = 0; // Budget for the trip
    private double maxBudget = 0;
    private double avgBudget;
    private double flightBudget;
    private int destination; // Foreign key referencing Country
    private String city;
    private int numMembers; // Number of members traveling
    private String weatherPreference; // Weather preference for the trip
    private String activityPreference; // Activity preferences
    private String cuisinePreference; // Cuisine preferences
    private String hotelPreference; // Hotel preference
    private String flightPreference; // Flight preference
    private String travelDay;
    private String travelMonth;
    private String travelYear;
     private int travelDuration;
    private String travelDurationUnit;
    private String travelPurpose;
    boolean isVisible =false;
    
    double  flight_p;
    double  restaurant_p;
    double activity_p;
    double  hotel_p;

    private Connection conn; // Database connection

    // Constructor that accepts a database connection
    public Questionaire(Connection conn, String userId) {
        this.conn = conn;
        this.userId = userId;
    }

    // Getters and setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDurationStart(LocalDate durationStart) {
        this.durationStart = durationStart;
    }

    public void setDurationEnd(LocalDate durationEnd) {
        this.durationEnd = durationEnd;
    }

    public double getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(double minBudget) {
        this.minBudget = minBudget;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

   

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public void setWeatherPreference(String weatherPreference) {
        this.weatherPreference = weatherPreference;
    }

    public void setActivityPreference(String activityPreference) {
        this.activityPreference = activityPreference;
    }

    public void setCuisinePreference(String cuisinePreference) {
        this.cuisinePreference = cuisinePreference;
    }

    public void setHotelPreference(String hotelPreference) {
        this.hotelPreference = hotelPreference;
    }

    public void setFlightPreference(String flightPreference) {
        this.flightPreference = flightPreference;
    }

    public double getAvgBudget() {
        return avgBudget;
    }

    public double getFlightBudget() {
        return flightBudget;
    }

    public String getWeatherPreference() {
        return weatherPreference;
    }

    public String getActivityPreference() {
        return activityPreference;
    }

    public String getFlightPreference() {
        return flightPreference;
    }
    
    

    // Method to show main info for user preferences
public void showMainInfo() {
    JFrame frame = new JFrame("Main Information");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(520, 700);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(220, 255, 220));

    // Add a border to the main frame
    frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(34, 139, 34)));

    // Create a header label
    JLabel headerLabel = new JLabel("Main Information");
    headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
    headerLabel.setForeground(new Color(34, 139, 34));
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding to the header

    frame.add(headerLabel, BorderLayout.NORTH);

    // Content panel with a softer border
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(new Color(220, 255, 220));
    contentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60, 179, 113)), "Please Fill the Details", TitledBorder.CENTER, TitledBorder.TOP, new Font("Times New Roman", Font.BOLD, 16), new Color(34, 139, 34)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel budgetLabel = new JLabel("Budget:");
    budgetLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
    budgetLabel.setForeground(new Color(34, 139, 34));
    contentPanel.add(budgetLabel, gbc);

    String[] budgetOptions = {
            "500-1000", "1000-5000", "5000-10000", "10000-100000",
            "100000-500000", "500000-1000000", "1000000-5000000", "Other"
    };
    JComboBox<String> budgetCombo = new JComboBox<>(budgetOptions);
    budgetCombo.setEditable(true);
    gbc.gridx = 1;
    contentPanel.add(budgetCombo, gbc);

    JTextField customBudgetField = new JTextField(10);
    customBudgetField.setVisible(false);
    gbc.gridx = 2;
    contentPanel.add(customBudgetField, gbc);

    JLabel travelersLabel = new JLabel("Number of Members:");
    travelersLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
    travelersLabel.setForeground(new Color(34, 139, 34));
    gbc.gridx = 0;
    gbc.gridy++;
    contentPanel.add(travelersLabel, gbc);

    String[] travelersOptions = new String[20];
    for (int i = 0; i < 20; i++) {
        travelersOptions[i] = String.valueOf(i + 1);
    }
    JComboBox<String> travelersCombo = new JComboBox<>(travelersOptions);
    travelersCombo.setEditable(true);
    gbc.gridx = 1;
    contentPanel.add(travelersCombo, gbc);

    JLabel durationLabel = new JLabel("Travel Duration:");
    durationLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
    durationLabel.setForeground(new Color(34, 139, 34));
    gbc.gridx = 0;
    gbc.gridy++;
    contentPanel.add(durationLabel, gbc);

    JPanel durationPanel = new JPanel(new FlowLayout());
    String[] durationOptions = new String[30];
    for (int i = 0; i < 30; i++) {
        durationOptions[i] = String.valueOf(i + 1);
    }
    JComboBox<String> durationCombo = new JComboBox<>(durationOptions);
    JComboBox<String> durationUnitCombo = new JComboBox<>(new String[]{"Days", "Weeks", "Months", "Years"});

    durationPanel.add(durationCombo);
    durationPanel.add(durationUnitCombo);
    gbc.gridx = 1;
    contentPanel.add(durationPanel, gbc);

    JLabel travelDateLabel = new JLabel("Travel Date:");
    travelDateLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
    travelDateLabel.setForeground(new Color(34, 139, 34));
    gbc.gridx = 0;
    gbc.gridy++;
    contentPanel.add(travelDateLabel, gbc);

    JPanel datePanel = new JPanel(new GridBagLayout());
    GridBagConstraints dateGbc = new GridBagConstraints();
    dateGbc.insets = new Insets(5, 5, 5, 5);
    dateGbc.fill = GridBagConstraints.HORIZONTAL;

    String[] days = new String[31];
    for (int i = 0; i < 31; i++) {
        if (i < 9) {
            days[i] = "0" + (i + 1);
        } else {
            days[i] = String.valueOf(i + 1);
        }
    }

    JComboBox<String> dayCombo = new JComboBox<>(days);
    dateGbc.gridx = 0;
    dateGbc.gridy = 1;
    datePanel.add(new JLabel("Day:"), dateGbc);
    dateGbc.gridx = 1;
    datePanel.add(dayCombo, dateGbc);

    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    JComboBox<String> monthCombo = new JComboBox<>(months);
    dateGbc.gridx = 0;
    dateGbc.gridy = 2;
    datePanel.add(new JLabel("Month:"), dateGbc);
    dateGbc.gridx = 1;
    datePanel.add(monthCombo, dateGbc);

    JComboBox<String> yearCombo = new JComboBox<>();
    for (int i = 2024; i <= 2050; i++) {
        yearCombo.addItem(String.valueOf(i));
    }
    dateGbc.gridx = 0;
    dateGbc.gridy = 3;
    datePanel.add(new JLabel("Year:"), dateGbc);
    dateGbc.gridx = 1;
    datePanel.add(yearCombo, dateGbc);

    gbc.gridx = 1;
    contentPanel.add(datePanel, gbc);

    JLabel purposeLabel = new JLabel("Travel Purpose:");
    purposeLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
    purposeLabel.setForeground(new Color(34, 139, 34));
    gbc.gridx = 0;
    gbc.gridy++;
    contentPanel.add(purposeLabel, gbc);

    String[] purposes = {
            "Select", "Study", "Photography", "Work", "Medical Treatment", "Leisure",
            "Tourism", "Volunteering", "Visiting Family", "Research",
            "Study Abroad", "Physical Therapy", "Entertainment", "Other"
    };
    JComboBox<String> purposeCombo = new JComboBox<>(purposes);
    gbc.gridx = 1;
    contentPanel.add(purposeCombo, gbc);

    budgetCombo.addActionListener(e -> {
        customBudgetField.setVisible("Other".equals(budgetCombo.getSelectedItem()));
        frame.revalidate();
        frame.repaint();
    });

    JSeparator separator = new JSeparator();
    separator.setForeground(Color.GRAY);
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    contentPanel.add(separator, gbc);
    gbc.gridwidth = 1;

    JButton nextButton = new JButton("Next");
    styleButton(nextButton);
    gbc.gridx = 1;
    gbc.gridy++;
    contentPanel.add(nextButton, gbc);

    nextButton.addActionListener(e -> {
        boolean validInput = true;

        String selectedBudget = (String) budgetCombo.getSelectedItem();
        if (selectedBudget.trim().isEmpty() || ("Other".equals(selectedBudget) && customBudgetField.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(frame, "Please enter a budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        } else if ("Other".equals(selectedBudget)) {
            try {
                maxBudget = Double.parseDouble(customBudgetField.getText());
                if (maxBudget <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid budget greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            }
        } else {
            minBudget = Double.parseDouble(selectedBudget.split("-")[0]);
            maxBudget = Double.parseDouble(selectedBudget.split("-")[1]);
        }

        String selectedTravelers = (String) travelersCombo.getSelectedItem();
        if (selectedTravelers == null || selectedTravelers.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select the number of members.", "Input Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        } else {
            numMembers = Integer.parseInt(selectedTravelers);
        }

        String selectedDuration = (String) durationCombo.getSelectedItem();
        if (selectedDuration == null || selectedDuration.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select the travel duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        } else {
            travelDuration = Integer.parseInt(selectedDuration);
            travelDurationUnit = (String) durationUnitCombo.getSelectedItem();
        }

        travelDay = (String) dayCombo.getSelectedItem();
        travelMonth = (String) monthCombo.getSelectedItem();
        travelYear = (String) yearCombo.getSelectedItem();
        StartDateParse(travelDay, travelMonth, travelYear);

        travelPurpose = (String) purposeCombo.getSelectedItem();
        if ("Select".equals(travelPurpose)) {
            JOptionPane.showMessageDialog(frame, "Please select a travel purpose.", "Input Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        }

        if(validInput) {	
	savePreferencesToDatabase1();
         
	 frame.dispose(); // Close the main info frame
	 showPreferences(); // Show preferences window
	 }

    });

    frame.add(contentPanel, BorderLayout.CENTER);
    frame.setVisible(true);
}

public void showPreferences() {
    JFrame frame = new JFrame("Preferences");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(520, 700); // Match the size of showMainInfo
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(220, 255, 220));

    // Add a border to the main frame
    frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(34, 139, 34)));

    // Create a header label
    JLabel headerLabel = new JLabel("User Preferences");
    headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
    headerLabel.setForeground(new Color(34, 139, 34));
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding to the header

    frame.add(headerLabel, BorderLayout.NORTH);

    // Content panel with a softer border
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridBagLayout());
    formPanel.setBackground(new Color(220, 255, 220));
    formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60, 179, 113)), "Please Fill Your Preferences", TitledBorder.CENTER, TitledBorder.TOP, new Font("Times New Roman", Font.BOLD, 16), new Color(34, 139, 34)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    // Questions for preferences
    String[] questions = {
        "Preferred Cuisine:",
        "Preferred Activity:",
        "Preferred Weather:",
        "What hotel type do you prefer?",
        "What flight type do you prefer?",
        "Do you have a specific Destination to visit in Saudi Arabia?"
    };

    // Options for each question
    String[][] options = {
        {"Select", "Italian", "Chinese", "Indian", "Mexican", "American", "French", 
         "Japanese", "Thai", "Spanish", "Greek", "Lebanese", "Turkish", 
         "Vietnamese", "Korean", "Mediterranean", "Brazilian", "Caribbean"},
        {"Select", "Sightseeing", "Adventure", "Relaxation", "Cultural", "Food Tours", 
         "Shopping", "Beach Activities", "Sports", "Wildlife Safari", 
         "Cruises", "Wellness Retreats", "Historical Tours", "Road Trips"},
        {"Select", "Sunny", "Rainy", "Cold", "Mild", "Hot", "Humid", "Dry", 
         "Windy", "Snowy", "Foggy", "Pleasant"},
        {"Select", "Standard", "Luxury"},
        {"Select", "Standard", "Luxury"},
        {"Select", "Yes", "No"}
    };

    JComboBox<String>[] preferenceCombos = new JComboBox[questions.length];

    // Loop through the questions and add them to the form panel
    for (int i = 0; i < questions.length; i++) {
        final int index = i;
        JLabel questionLabel = new JLabel(questions[i]);
        questionLabel.setForeground(new Color(34, 139, 34));
        preferenceCombos[i] = new JComboBox<>(options[i]);

        gbc.gridx = 0;
        gbc.gridy = i;
        formPanel.add(questionLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(preferenceCombos[i], gbc);
    }

    // Country list
    JLabel countryLabel = new JLabel("Select a City:");
    countryLabel.setForeground(new Color(34, 139, 34));
    JComboBox<String> countryCombo = new JComboBox<>(new String[] {
        "Select", "Riyadh", "Jeddah", "Mecca", "Medina", "Dammam", "Khobar", "Tabuk", 
        "Hail", "Abha", "Jizan", "Najran", "Al-Qassim", "Taif"
    });

    countryLabel.setVisible(false);

 countryCombo.setVisible(false);

 // Show or hide the country selection based on the user's answer to "Do you have a specific country in mind?"

// Show or hide the country selection based on the user's answer to "Do you have a specific Destination to visit in Saudi Arabia?"

preferenceCombos[5].addActionListener(e-> {

 isVisible= "Yes".equals(preferenceCombos[5].getSelectedItem());

 countryLabel.setVisible(isVisible);

 countryCombo.setVisible(isVisible);

 frame.revalidate();

 frame.repaint();

 // Reset city when "No" is selected or no city is chosen yet

 if(!isVisible) {

 city= null; // Reset city if "No" is selected

 }

});


countryCombo.addActionListener(e-> {



 String selectedCity = (String) countryCombo.getSelectedItem();



 if (!"Select".equals(selectedCity)) {



 city = selectedCity;



 System.out.println("Selected city: " + city); // Debug output to confirm selection



 }



});


    // Add country selection components to the form
    gbc.gridy = questions.length;
    gbc.gridx = 0;
    formPanel.add(countryLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(countryCombo, gbc);

    // Back Button
    JButton backButton = new JButton("Back");
    styleButton(backButton);

    backButton.addActionListener(e -> {
        frame.dispose();
        showMainInfo(); // Go back to main info without losing answers
    });

    // Submit Button
    JButton submitButton = new JButton("Continue");
    styleButton(submitButton);

    submitButton.addActionListener(e -> {
        // Validate user selections
        boolean validInput = true;
        StringBuilder errorMessage = new StringBuilder("Please select an option for:\n");

        // Check if preferences have been selected and if default "Select" is chosen
        for (int i = 0; i < preferenceCombos.length - 1; i++) { // Exclude the last combo (country)
            if ("Select".equals(preferenceCombos[i].getSelectedItem())) {
                errorMessage.append(questions[i]).append("\n");
                validInput = false;
            }
        }

        // Check if a country has been selected (if "Yes" was chosen for the specific country question)
        if ("Yes".equals(preferenceCombos[5].getSelectedItem()) && "Select".equals(countryCombo.getSelectedItem())) {
            errorMessage.append("Select a city.\n");
            validInput = false;
        }

        if (!validInput) {
            JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If all inputs are valid, save user preferences
        weatherPreference = (String) preferenceCombos[2].getSelectedItem(); // Weather preference
        activityPreference = (String) preferenceCombos[1].getSelectedItem(); // Activity preference
        cuisinePreference = (String) preferenceCombos[0].getSelectedItem(); // Cuisine preference
        hotelPreference = (String) preferenceCombos[3].getSelectedItem(); // Hotel preference
        flightPreference = (String) preferenceCombos[4].getSelectedItem(); // Flight preference
        
        // Save the user preferences to the database
        

savePreferencesToDatabase2();



 budgetSplit(flightPreference, hotelPreference);



 calcAvgbudget(minBudget, maxBudget);



 calcflightBudget( avgBudget, flight_p);



 saveCitiestoDB();

        // Show confirmation dialog
        JOptionPane.showMessageDialog(frame, "Preferences saved successfully!");

        // Close the preferences frame
        frame.dispose();
        TravelAppGUI Homepage = new TravelAppGUI (conn, userId);
        Homepage.showPlansPageafterAddNew();
    });

    // Adding buttons to a panel at the bottom
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    buttonPanel.setBackground(new Color(220, 255, 220));
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    // Add form panel and button panel to the frame
    frame.add(formPanel, BorderLayout.CENTER);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    // Set the frame visible
    frame.setVisible(true);
}

// دالة لتنسيق الأزرار
private void styleButton(JButton button) {
    button.setFont(new Font("Times New Roman", Font.BOLD, 18));
    button.setBackground(new Color(60, 179, 113));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setOpaque(true);
}




    // Method to save user preferences to the database
private void savePreferencesToDatabase2() {
    String query = "INSERT INTO trips (userId, weather_preference, activity_preference, cuisine_preference, hotel_preference, flight_preference) " +
                   "VALUES (?, ?, ?, ?, ?, ?) " +
                   "ON DUPLICATE KEY UPDATE " +
                   "weather_preference = VALUES(weather_preference), " +
                   "activity_preference = VALUES(activity_preference), " +
                   "cuisine_preference = VALUES(cuisine_preference), " +
                   "hotel_preference = VALUES(hotel_preference), " +
                   "flight_preference = VALUES(flight_preference)"; 

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId);  // Use the userId from the currentUser object
        pstmt.setString(2, weatherPreference);
        pstmt.setString(3, activityPreference);
        pstmt.setString(4, cuisinePreference);
        pstmt.setString(5, hotelPreference);
        pstmt.setString(6, flightPreference);

        pstmt.executeUpdate();
        //JOptionPane.showMessageDialog(null, "Preferences saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving preferences: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


       private void savePreferencesToDatabase1() {
    String query = "INSERT INTO trips (userId, purpose, duration_start, travelDuration, travelDurationUnit, minBudget, maxBudget, num_members) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                   "ON DUPLICATE KEY UPDATE " +
                   "purpose = ?, " +
                   "duration_start = ?, " +
                   "travelDuration = ?, " +
                   "travelDurationUnit = ?, " +
                   "minBudget = ?, " +
                   "maxBudget = ?, " +
                   "num_members = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId);
        pstmt.setString(2, travelPurpose);
        pstmt.setObject(3, java.sql.Date.valueOf(durationStart));  // Assume durationStart is a valid LocalDate
        pstmt.setObject(4, travelDuration);
        pstmt.setObject(5, travelDurationUnit);
        pstmt.setDouble(6, minBudget);
        pstmt.setDouble(7, maxBudget);
        pstmt.setInt(8, numMembers);

        // Set parameters for the ON DUPLICATE KEY UPDATE part
        pstmt.setString(9, travelPurpose);
        pstmt.setObject(10, java.sql.Date.valueOf(durationStart));
        pstmt.setObject(11, travelDuration);
        pstmt.setObject(12, travelDurationUnit);
        pstmt.setDouble(13, minBudget);
        pstmt.setDouble(14, maxBudget);
        pstmt.setInt(15, numMembers);

        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving preferences: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    void budgetSplit(String f, String h) {
    // Ensure comparison is case-insensitive and correct the method calls
    if (f.equalsIgnoreCase("standard") && h.equalsIgnoreCase("standard")) {
        hotel_p = 0.25;
        restaurant_p = 0.25;
        activity_p = 0.25;
        flight_p = 0.25;

    } else if (f.equalsIgnoreCase("standard") && h.equalsIgnoreCase("luxury")) {
        hotel_p = 0.40;
        restaurant_p = 0.20;
        activity_p = 0.20;
        flight_p = 0.20;

    } else if (f.equalsIgnoreCase("luxury") && h.equalsIgnoreCase("standard")) {
        flight_p = 0.40;
        restaurant_p = 0.20;
        activity_p = 0.20;
        hotel_p = 0.20;

    } else if (f.equalsIgnoreCase("luxury") && h.equalsIgnoreCase("luxury")) {
        flight_p = 0.40;
        restaurant_p = 0.10;
        activity_p = 0.10;
        hotel_p = 0.40;
    }
}
   
    double calcAvgbudget(double minBudget, double maxBudget){
        if (minBudget == 0)
            avgBudget = maxBudget;
        else 
           avgBudget= (minBudget + maxBudget)/2;
       
        return avgBudget;
    };
    
    double calcflightBudget(double avgBudget, double flight_p) {
     
        flightBudget = avgBudget * flight_p;
         return flightBudget;
    }
    
    LocalDate StartDateParse (String travelday, String travelMonth, String travelYear){
        String stringDate= travelYear + "-" + travelMonth + "-" + travelday;
        durationStart = LocalDate.parse(stringDate);
        
        return durationStart;
    }
    
// Method to save each country into the travel plan
private void savetotravelplan(String userId, int tripId, int countryId, String city) {
    String query = "INSERT INTO travelplans (userId, tripId, countryId, city) " +
                   "VALUES (?, ?, ?, ?) " +
                   "ON DUPLICATE KEY UPDATE countryId = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId);
        pstmt.setInt(2, tripId);
        pstmt.setInt(3, countryId);
        pstmt.setString(4, city);
        pstmt.setInt(5, countryId); // Set the fifth parameter for the duplicate key update

        pstmt.executeUpdate();
        System.out.println("Country suggestion saved for travel plan with tripId " + tripId + " and userId " + userId);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving travel plan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public int getTripIdByUserId(String userId) {
    String query = "SELECT tripId FROM trips WHERE userId = ?";
    int tripId = -1; // Default value if no tripId is found

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId); // Set the userId parameter
        ResultSet rs = pstmt.executeQuery();

        // Check if there's a result, and retrieve tripId
        if (rs.next()) {
            tripId = rs.getInt("tripId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving trip ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return tripId; // Default value if no tripId is found
}
    public int[] getTravelPlanIDByUserId(String userId) {
    String query = "SELECT planId FROM travelplans WHERE userId = ?";
    int[] tvID = new int[100]; // Default value if no tripId is found

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId); // Set the userId parameter
        ResultSet rs = pstmt.executeQuery();

        // Check if there's a result, and retrieve tripId
        int i=0;
        while (rs.next()) {
          tvID[i] = rs.getInt("planId");
          i++;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving trip ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return tvID; // Return the found tripId or -1 if none found
}
    
 public String getCityNamefromTPbyUserID(String userId) {
    String query = "SELECT city FROM travelplans WHERE userId = ?";
    String TP = ""; // Default value if no tripId is found

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId); // Set the userId parameter
        ResultSet rs = pstmt.executeQuery();

        // Check if there's a result, and retrieve tripId
        if (rs.next()) {
            TP = rs.getString("city");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving trip ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return TP; // Default value if no tripId is found
} 
 
     public String[] getcityNames(String userId) {
    String query = "SELECT city FROM travelplans WHERE userId = ?";

    List<String> cityNamesList = new ArrayList<>(); // Create a list to store city names
    
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId); // Set the userId parameter
        ResultSet rs = pstmt.executeQuery();

        // Retrieve city names from the result set
        while (rs.next()) {
            cityNamesList.add(rs.getString("city")); // Add each city to the list
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving city names: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Convert the list to an array and return
    return cityNamesList.toArray(new String[0]); // Returns an array containing all cities
}
   private void saveCitiestoDB() {// save cities to travelplan
    Countries country = new Countries(conn); // Create Country object with DB connection

    // Get the list of available countries and cities
    List<String[]> availableCountries = country.selectCountry(getFlightBudget(), getWeatherPreference(), getActivityPreference());
    
    // Display the country and city names, and save each to the travel plan
    if (isVisible==false) {
        //System.out.println("Here are some cities in Saudi Arabia that match your preferences:");
        for (String[] pair : availableCountries) {
            String countryName = pair[0];
            String cityName = pair[1];
            int countryId = country.getCountryIdByName(cityName);

            //System.out.println("Country: " + countryName + ", City: " + cityName);
            savetotravelplan(userId,getTripIdByUserId(userId), countryId, cityName);
        }
    } else { //save the choice selected
       int countryId = country.getCountryIdByName(city);
if (countryId > 0) {  // Assume 0 means invalid or non-existent
    savetotravelplan(userId, getTripIdByUserId(userId), countryId, city);
} else {
    System.out.println("Invalid countryId for city: " + city);
}
    }
    
   }
  


}
   
   
    

