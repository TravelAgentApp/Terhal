
package terhal;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


public class Questionaire {
    private String[] preferences = new String[4];
    private double budget;
    private int numberOfTravelers;
    private int travelDuration;
    private String travelDurationUnit;
    private String travelPurpose;
    private String specificCountry;
    private String hotelType; // نوع الفندق
    private String tripType;   // نوع الرحلة
    private int travelDay;
    private int travelMonth;
    private int travelYear;

    // Getters and Setters
    public String[] getPreferences() {
        return preferences;
    }

    public double getBudget() {
        return budget;
    }

    public int getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public int getTravelDuration() {
        return travelDuration;
    }

    public String getTravelDurationUnit() {
        return travelDurationUnit;
    }

    public String getTravelPurpose() {
        return travelPurpose;
    }

    public void setPreference(int index, String value) {
        preferences[index] = value;
    }

  public void showMainInfo() {
    JFrame frame = new JFrame("Main Information");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(500, 600); // Adjust the frame size for better visibility
    frame.setLayout(new BorderLayout(5, 5)); // Reduced margins between components

    // Main panel to hold form elements
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridLayout(9, 2, 5, 5)); // Reduced gaps for spacing
    formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the form
    formPanel.setBackground(Color.decode("#f0f8ff")); // Light blue background

    // Budget
    JLabel budgetLabel = new JLabel("Budget:");
    budgetLabel.setForeground(Color.decode("#000080")); // Navy text color
    String[] budgetOptions = {
        "500-1000", "1000-5000", "5000-10000", "10000-100000", 
        "100000-500000", "500000-1000000", "1000000-5000000", "Other"
    };
    JComboBox<String> budgetCombo = new JComboBox<>(budgetOptions);

    // Input field for "Other" budget option
    JLabel customBudgetLabel = new JLabel("Enter Custom Budget:");
    JTextField customBudgetField = new JTextField();
    customBudgetLabel.setVisible(false); // Hidden initially
    customBudgetField.setVisible(false); // Hidden initially

    // Show or hide the custom budget input field based on the selected option
    budgetCombo.addActionListener(e -> {
        if ("Other".equals(budgetCombo.getSelectedItem())) {
            customBudgetLabel.setVisible(true);
            customBudgetField.setVisible(true);
        } else {
            customBudgetLabel.setVisible(false);
            customBudgetField.setVisible(false);
        }
        frame.revalidate();
        frame.repaint();
    });

    // Number of Travelers
    JLabel travelersLabel = new JLabel("Number of Members:");
    travelersLabel.setForeground(Color.decode("#000080")); // Navy text color
    String[] travelersOptions = new String[20]; // Keep the number of travelers to 20
    for (int i = 0; i < 20; i++) {
        travelersOptions[i] = String.valueOf(i + 1);
    }
    JComboBox<String> travelersCombo = new JComboBox<>(travelersOptions);
    travelersCombo.setEditable(true);

    // Travel Duration
    JLabel durationLabel = new JLabel("Travel Duration:");
    durationLabel.setForeground(Color.decode("#000080")); // Navy text color
    JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

    String[] durationOptions = new String[30]; // Options from 1 to 30 for duration
    for (int i = 0; i < 30; i++) {
        durationOptions[i] = String.valueOf(i + 1);
    }
    JComboBox<String> durationCombo = new JComboBox<>(durationOptions);
    durationCombo.setPreferredSize(new Dimension(50, 25)); // Set preferred size

    // Combo box for duration units
    String[] durationUnits = {"Days", "Weeks", "Months", "Years"};
    JComboBox<String> durationUnitCombo = new JComboBox<>(durationUnits);
    durationUnitCombo.setPreferredSize(new Dimension(80, 25)); // Set preferred size

    durationPanel.add(durationCombo);
    durationPanel.add(durationUnitCombo);

    // Travel Date
    JLabel travelDateLabel = new JLabel("Travel Date:");
    travelDateLabel.setForeground(Color.decode("#000080")); // Navy text color
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

    // Day selection
    String[] days = new String[30];
    for (int i = 0; i < 30; i++) {
        days[i] = String.valueOf(i + 1);
    }
    JComboBox<String> dayCombo = new JComboBox<>(days);
    dayCombo.setPreferredSize(new Dimension(50, 25)); // Set preferred size

    // Month selection
    String[] months = new String[12];
    for (int i = 0; i < 12; i++) {
        months[i] = String.valueOf(i + 1);
    }
    JComboBox<String> monthCombo = new JComboBox<>(months);
    monthCombo.setPreferredSize(new Dimension(50, 25)); // Set preferred size

    // Year selection
    String[] years = new String[27];
    for (int i = 0; i < 27; i++) {
        years[i] = String.valueOf(2024 + i);
    }
    JComboBox<String> yearCombo = new JComboBox<>(years);
    yearCombo.setPreferredSize(new Dimension(70, 25)); // Set preferred size

    datePanel.add(dayCombo);
    datePanel.add(monthCombo);
    datePanel.add(yearCombo);

    // Travel Purpose
    JLabel purposeLabel = new JLabel("Travel Purpose:");
    purposeLabel.setForeground(Color.decode("#000080")); // Navy text color
    String[] purposes = {
        "Study", "Photography", "Work", "Medical Treatment", "Leisure",
        "Tourism", "Volunteering", "Visiting Family", "Research", 
        "Study Abroad", "Physical Therapy", "Entertainment", "Other", "Additional"
    };
    JComboBox<String> purposeCombo = new JComboBox<>(purposes);

    // Adding components to the form panel
    formPanel.add(budgetLabel);
    formPanel.add(budgetCombo);
    formPanel.add(customBudgetLabel);
    formPanel.add(customBudgetField); // Add custom budget input field
    formPanel.add(travelersLabel);
    formPanel.add(travelersCombo);
    formPanel.add(durationLabel);
    formPanel.add(durationPanel);
    formPanel.add(travelDateLabel);
    formPanel.add(datePanel);
    formPanel.add(purposeLabel);
    formPanel.add(purposeCombo);

    // Next Button
    JButton nextButton = new JButton("Next");
    nextButton.setPreferredSize(new Dimension(100, 30));
    nextButton.setBackground(Color.decode("#32cd32")); // LimeGreen background
    nextButton.setForeground(Color.WHITE); // White text color

    // Action for Next Button
    nextButton.addActionListener(e -> {
        boolean validInput = true;

        // Validate budget
        String selectedBudget = (String) budgetCombo.getSelectedItem();
        if (selectedBudget.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select a budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        } else if ("Other".equals(selectedBudget)) {
            // If "Other" is selected, validate the custom budget field
            String enteredBudget = customBudgetField.getText().trim();
            if (enteredBudget.isEmpty() || Double.parseDouble(enteredBudget) <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid budget greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else {
                try {
                    budget = Double.parseDouble(enteredBudget);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                }
            }
        } else {
            // Parse predefined budget options if needed
            budget = Double.parseDouble(selectedBudget.replace("-", "").replace(",", "")); // Convert string to double
        }

        // Validate number of travelers
        if (validInput) {
            String selectedTraveler = (String) travelersCombo.getSelectedItem();
            if (selectedTraveler.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter the number of travelers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else {
                try {
                    numberOfTravelers = Integer.parseInt(selectedTraveler);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of travelers (1-20).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                }
            }
        }

        // Validate travel duration
        if (validInput) {
            String selectedDuration = (String) durationCombo.getSelectedItem();
            travelDuration = Integer.parseInt(selectedDuration);
            travelDurationUnit = (String) durationUnitCombo.getSelectedItem();
        }

        // Validate travel date
        if (validInput) {
            travelDay = Integer.parseInt((String) dayCombo.getSelectedItem());
            travelMonth = Integer.parseInt((String) monthCombo.getSelectedItem());
            travelYear = Integer.parseInt((String) yearCombo.getSelectedItem());
        }

        // If all inputs are valid, proceed to preferences
        if (validInput) {
            travelPurpose = (String) purposeCombo.getSelectedItem();
            frame.dispose(); // Close the main info frame
            showPreferences(); // Show preferences window
        }
    });

    // Footer panel for buttons
    JPanel footerPanel = new JPanel();
    footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    footerPanel.add(nextButton);

    // Add form panel and footer panel to the frame
    frame.add(formPanel, BorderLayout.CENTER); // Form in the center
    frame.add(footerPanel, BorderLayout.SOUTH); // Buttons at the bottom

    // Set the background color for the footer
    footerPanel.setBackground(Color.decode("#f0f8ff")); // Same as form panel

    // Show the frame
    frame.setVisible(true);
}


  public void showPreferences() {
    JFrame frame = new JFrame("Preferences");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLayout(new BorderLayout(5, 5)); // Adjust to BorderLayout for better spacing

    // Main panel to hold form elements
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridBagLayout());
    formPanel.setBackground(Color.decode("#f0f8ff")); // Light blue background
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Reduced padding around elements
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST; // Align to the left

    // Questions for preferences
    String[] questions = {
        "Preferred Cuisine:",
        "Preferred Activity:",
        "Preferred Weather:",
        "What hotel type do you prefer?",
        "What flight type do you prefer?",
        "Do you have a specific country in mind?"
    };

    // Options for each question
    String[][] options = {
        {
            " ","Italian", "Chinese", "Indian", "Mexican", "American", "French", 
            "Japanese", "Thai", "Spanish", "Greek", "Lebanese", "Turkish", 
            "Vietnamese", "Korean", "Mediterranean", "Brazilian", "Caribbean"
        },
        {
            " ","Sightseeing", "Adventure", "Relaxation", "Cultural", "Food Tours", 
            "Shopping", "Beach Activities", "Sports", "Wildlife Safari", 
            "Cruises", "Wellness Retreats", "Historical Tours", "Road Trips"
        },
        {
            " ","Sunny", "Rainy", "Cold", "Mild", "Hot", "Humid", "Dry", 
            "Windy", "Snowy", "Foggy", "Pleasant"
        },
        {
            " ","Standard", "Luxury"  // Hotel type options
        },
        {
            " ","Standard", "Luxury"  // Flight type options
        },
        {
            " ","Yes", "No"  // Options for "Do you have a specific country in mind?"
        }
    };

    JComboBox<String>[] preferenceCombos = new JComboBox[questions.length];

    // Loop through the questions and add them to the form panel
    for (int i = 0; i < questions.length; i++) {
        final int index = i;
        JLabel questionLabel = new JLabel(questions[i]);
        questionLabel.setForeground(Color.decode("#000080")); // Navy text color
        preferenceCombos[i] = new JComboBox<>(options[i]);

        gbc.gridx = 0;
        gbc.gridy = i;
        formPanel.add(questionLabel, gbc); // Add question label

        gbc.gridx = 1;
        formPanel.add(preferenceCombos[i], gbc); // Add combo box
    }

    // Country list
    JLabel countryLabel = new JLabel("Select Country:");
    countryLabel.setForeground(Color.decode("#000080")); // Navy text color
    JComboBox<String> countryCombo = new JComboBox<>(new String[] {
        "Egypt", "Jordan", "UAE", "Saudi Arabia", "Morocco", "Tunisia",
        "Lebanon", "Oman", "Qatar", "Bahrain", "Kuwait", "Iraq",
        "Algeria", "Sudan", "Syria", "USA", "Canada", "UK", "France",
        "Germany", "Italy", "Spain", "Australia", "Japan", "South Korea",
        "Brazil", "India", "China", "Russia", "Mexico", "South Africa",
        "New Zealand"
    });

    // Initially hide the country selection combo box
    countryLabel.setVisible(false);
    countryCombo.setVisible(false);

    // Show or hide the country selection based on the user's answer to "Do you have a specific country in mind?"
    preferenceCombos[5].addActionListener(e -> {
        if ("Yes".equals(preferenceCombos[5].getSelectedItem())) {
            countryLabel.setVisible(true);
            countryCombo.setVisible(true);
        } else {
            countryLabel.setVisible(false);
            countryCombo.setVisible(false);
        }
        frame.revalidate();
        frame.repaint();
        
    });

    // Add country selection components to the form
    gbc.gridy = questions.length;
    gbc.gridx = 0;
    formPanel.add(countryLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(countryCombo, gbc);

    // Back Button
    JButton backButton = new JButton("Back");
    backButton.setPreferredSize(new Dimension(100, 30)); // Set size
    backButton.setBackground(Color.decode("#ff6347")); // Tomato background
    backButton.setForeground(Color.WHITE); // White text color
    backButton.addActionListener(e -> {
        frame.dispose();
        showMainInfo(); // Go back to main info without losing answers
    });

    // Submit Button
    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(100, 30)); // Set size
    submitButton.setBackground(Color.decode("#32cd32")); // LimeGreen background
    submitButton.setForeground(Color.WHITE); // White text color

    submitButton.addActionListener(e -> {
    boolean isAnyEmpty = false;

    for (int i = 0; i < preferenceCombos.length; i++) {
        if (preferenceCombos[i].getSelectedItem().equals(" ")) {
            isAnyEmpty = true;
            break;
        }
    }

    if (isAnyEmpty) {
        JOptionPane.showMessageDialog(null, "Please select something for each question before submitting the form.");
    } else {
        frame.dispose();
        // Call TravelPlan to show the travel plan
    }
    });
        // Call to TravelPlan to show the travel plan

    // Adding buttons to a panel at the bottom
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    buttonPanel.setBackground(Color.decode("#f0f8ff")); // Same background as the form
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);

    // Add form panel and button panel to the frame
    frame.add(formPanel, BorderLayout.CENTER);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    // Set the frame visible
    frame.setVisible(true);
}

     public static void main(String[] args) {
        Questionaire q=new Questionaire();
        q.showMainInfo();
        
    }

}
