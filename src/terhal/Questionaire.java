
package terhal;

import javax.swing.*;
import java.awt.*;

public class Questionaire {
    private String[] preferences = new String[4];
    private double budget;
    private int numberOfTravelers;
    private int travelDuration;
    private String travelDurationUnit;
    private String travelPurpose;
    private String specificCountry;
    private int travelDay;
    private int travelMonth;
    private int travelYear;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new GridLayout(9, 2));

        // Budget
        JLabel budgetLabel = new JLabel("Budget:");
        String[] budgetOptions = {
            "500-1000", "1000-5000", "5000-10000", "10000-100000", 
            "100000-500000", "500000-1000000", "1000000-5000000", "Other"
        };
        JComboBox<String> budgetCombo = new JComboBox<>(budgetOptions);
        budgetCombo.setEditable(true);

        // Number of Travelers
        JLabel travelersLabel = new JLabel("Number of Members:");
        String[] travelersOptions = new String[20]; // Keep the number of travelers to 20
        for (int i = 0; i < 20; i++) {
            travelersOptions[i] = String.valueOf(i + 1);
        }
        JComboBox<String> travelersCombo = new JComboBox<>(travelersOptions);
        travelersCombo.setEditable(true);

        // Travel Duration
        JLabel durationLabel = new JLabel("Travel Duration:");
        JPanel durationPanel = new JPanel(new FlowLayout());
        
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
        JPanel datePanel = new JPanel(new FlowLayout());

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
        String[] purposes = {
            "Study", "Photography", "Work", "Medical Treatment", "Leisure",
            "Tourism", "Volunteering", "Visiting Family", "Research", 
            "Study Abroad", "Physical Therapy", "Entertainment", "Other", "Additional"
        };
        JComboBox<String> purposeCombo = new JComboBox<>(purposes);

        // Specific Country
        JLabel countryLabel = new JLabel("Have a specific country in mind?");
        JComboBox<String> countryMindCombo = new JComboBox<>(new String[] {"Yes", "No"});
        countryMindCombo.setPreferredSize(new Dimension(80, 25)); // Set preferred size

        JLabel countryDropdownLabel = new JLabel("Choose a Country:");
        JComboBox<String> countryCombo = new JComboBox<>(new String[] {
            "Egypt", "Jordan", "UAE", "Saudi Arabia", "Morocco", "Tunisia",
            "Lebanon", "Oman", "Qatar", "Bahrain", "Kuwait", "Iraq",
            "Algeria", "Sudan", "Syria", "USA", "Canada", "UK", "France",
            "Germany", "Italy", "Spain", "Australia", "Japan", "South Korea",
            "Brazil", "India", "China", "Russia", "Mexico", "South Africa",
            "New Zealand"
        });
        countryCombo.setVisible(false);

        // Next Button
        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            boolean validInput = true;

            // Validate budget
            String selectedBudget = (String) budgetCombo.getSelectedItem();
            if (selectedBudget.trim().isEmpty() || (selectedBudget.equals("Other") && budgetCombo.getEditor().getItem().toString().trim().isEmpty())) {
                JOptionPane.showMessageDialog(frame, "Please enter a budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else if (selectedBudget.equals("Other")) {
                try {
                    budget = Double.parseDouble(budgetCombo.getEditor().getItem().toString());
                    if (budget <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid budget greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                }
            } else {
                budget = 0; // Default
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
                if (countryMindCombo.getSelectedItem().equals("Yes")) {
                    specificCountry = (String) countryCombo.getSelectedItem();
                }
                frame.dispose();
                showPreferences();
            }
        });

        // Show/hide country combo based on selection
        countryMindCombo.addActionListener(e -> {
            countryCombo.setVisible(countryMindCombo.getSelectedItem().equals("Yes"));
            countryDropdownLabel.setVisible(countryMindCombo.getSelectedItem().equals("Yes"));
            frame.revalidate();
            frame.repaint();
        });

        // Adding components to frame
        frame.add(budgetLabel);
        frame.add(budgetCombo);
        frame.add(travelersLabel);
        frame.add(travelersCombo);
        frame.add(durationLabel);
        frame.add(durationPanel);
        frame.add(travelDateLabel);
        frame.add(datePanel);
        frame.add(purposeLabel);
        frame.add(purposeCombo);
        frame.add(countryLabel);
        frame.add(countryMindCombo);
        frame.add(countryDropdownLabel);
        frame.add(countryCombo);
        frame.add(new JLabel());

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30)); // Set size
        backButton.addActionListener(e -> {
            frame.dispose();
            showMainInfo(); // Go back to main info without losing answers
        });
        nextButton.setPreferredSize(new Dimension(100, 30)); // Set size

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    public void showPreferences() {
        JFrame frame = new JFrame("Preferences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 2));

        String[] questions = {
            "Preferred Cuisine:",
            "Preferred Activity:",
            "Preferred Weather:",
            "Preferred Destination:"
        };

        String[][] options = {
            {
                "Italian", "Chinese", "Indian", "Mexican", "American", "French", 
                "Japanese", "Thai", "Spanish", "Greek", "Lebanese", "Turkish", 
                "Vietnamese", "Korean", "Mediterranean", "Brazilian", "Caribbean"
            },
            {
                "Sightseeing", "Adventure", "Relaxation", "Cultural", "Food Tours", 
                "Shopping", "Beach Activities", "Sports", "Wildlife Safari", 
                "Cruises", "Wellness Retreats", "Historical Tours", "Road Trips"
            },
            {
                "Sunny", "Rainy", "Cold", "Mild", "Hot", "Humid", "Dry", 
                "Windy", "Snowy", "Foggy", "Pleasant"
            },
            {
                "Egypt", "Jordan", "UAE", "Saudi Arabia", "Morocco", "Tunisia", 
                "Lebanon", "Oman", "Qatar", "Bahrain", "Kuwait", "Iraq", "Algeria", 
                "Sudan", "Syria", "USA", "Canada", "UK", "France", "Germany", 
                "Italy", "Spain", "Australia", "Japan", "South Korea", 
                "Brazil", "India", "China", "Russia", "Mexico", "South Africa", 
                "New Zealand"
            }
        };

        for (int i = 0; i < questions.length; i++) {
            final int index = i;
            JLabel questionLabel = new JLabel(questions[i]);
            JComboBox<String> preferenceCombo = new JComboBox<>(options[i]);
            preferenceCombo.addActionListener(e -> setPreference(index, (String) preferenceCombo.getSelectedItem()));
            frame.add(questionLabel);
            frame.add(preferenceCombo);
        }

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30)); // Set size
        backButton.addActionListener(e -> {
            frame.dispose();
            showMainInfo(); // Go back to main info without losing answers
        });

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30)); // Set size
        submitButton.addActionListener(e -> {
            frame.dispose();
            // Call to TravelPlan to show the travel plan
        });

        frame.add(new JLabel());
        frame.add(backButton);
        frame.add(new JLabel());
        frame.add(submitButton);
        frame.setVisible(true);
    }
}