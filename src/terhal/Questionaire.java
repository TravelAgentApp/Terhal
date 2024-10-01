
package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());

        // Change background color
        frame.getContentPane().setBackground(Color.decode("#f0f8ff"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Budget
        JLabel budgetLabel = new JLabel("Budget:");
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 14));
        budgetLabel.setForeground(Color.decode("#000080"));
        gbc.gridx = 0;
        frame.add(budgetLabel, gbc);

        String[] budgetOptions = {
            "500-1000", "1000-5000", "5000-10000", "10000-100000",
            "100000-500000", "500000-1000000", "1000000-5000000", "Other"
        };
        JComboBox<String> budgetCombo = new JComboBox<>(budgetOptions);
        budgetCombo.setEditable(true);
        gbc.gridx = 1;
        frame.add(budgetCombo, gbc);

        // Custom budget input
        JTextField customBudgetField = new JTextField(10);
        customBudgetField.setVisible(false);
        gbc.gridx = 2;
        frame.add(customBudgetField, gbc);

        // Number of Travelers
        JLabel travelersLabel = new JLabel("Number of Members:");
        travelersLabel.setFont(new Font("Arial", Font.BOLD, 14));
        travelersLabel.setForeground(Color.decode("#000080"));
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(travelersLabel, gbc);

        String[] travelersOptions = new String[20];
        for (int i = 0; i < 20; i++) {
            travelersOptions[i] = String.valueOf(i + 1);
        }
        JComboBox<String> travelersCombo = new JComboBox<>(travelersOptions);
        travelersCombo.setEditable(true);
        gbc.gridx = 1;
        frame.add(travelersCombo, gbc);

        // Travel Duration
        JLabel durationLabel = new JLabel("Travel Duration:");
        durationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        durationLabel.setForeground(Color.decode("#000080"));
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(durationLabel, gbc);

        JPanel durationPanel = new JPanel(new FlowLayout());
        String[] durationOptions = new String[30];
        for (int i = 0; i < 30; i++) {
            durationOptions[i] = String.valueOf(i + 1);
        }
        JComboBox<String> durationCombo = new JComboBox<>(durationOptions);
        JComboBox<String> durationUnitCombo = new JComboBox<>(new String[] {"Days", "Weeks", "Months", "Years"});

        durationPanel.add(durationCombo);
        durationPanel.add(durationUnitCombo);
        gbc.gridx = 1;
        frame.add(durationPanel, gbc);

        // Travel Date
        JLabel travelDateLabel = new JLabel("Travel Date:");
        travelDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        travelDateLabel.setForeground(Color.decode("#000080"));
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(travelDateLabel, gbc);

        // Panel for date selection
        JPanel datePanel = new JPanel(new GridBagLayout());
        GridBagConstraints dateGbc = new GridBagConstraints();
        dateGbc.insets = new Insets(5, 5, 5, 5);
        dateGbc.fill = GridBagConstraints.HORIZONTAL;

        // Calendar type selection
        JComboBox<String> calendarTypeCombo = new JComboBox<>(new String[]{"Gregorian", "Hijri"});
        dateGbc.gridx = 0;
        dateGbc.gridy = 0;
        datePanel.add(new JLabel("Select Calendar:"), dateGbc);
        dateGbc.gridx = 1;
        datePanel.add(calendarTypeCombo, dateGbc);

        // Days selection
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        JComboBox<String> dayCombo = new JComboBox<>(days);
        dateGbc.gridx = 0;
        dateGbc.gridy = 1;
        datePanel.add(new JLabel("Day:"), dateGbc);
        dateGbc.gridx = 1;
        datePanel.add(dayCombo, dateGbc);

        // Months selection
        String[] months = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        dateGbc.gridx = 0;
        dateGbc.gridy = 2;
        datePanel.add(new JLabel("Month:"), dateGbc);
        dateGbc.gridx = 1;
        datePanel.add(monthCombo, dateGbc);

        // Years selection
        JComboBox<String> yearCombo = new JComboBox<>();
        dateGbc.gridx = 0;
        dateGbc.gridy = 3;
        datePanel.add(new JLabel("Year:"), dateGbc);
        dateGbc.gridx = 1;
        datePanel.add(yearCombo, dateGbc);

        calendarTypeCombo.addActionListener(e -> {
            yearCombo.removeAllItems();
            if ("Gregorian".equals(calendarTypeCombo.getSelectedItem())) {
                for (int i = 2024; i <= 2050; i++) {
                    yearCombo.addItem(String.valueOf(i));
                }
            } else {
                for (int i = 1446; i <= 1472; i++) {
                    yearCombo.addItem(String.valueOf(i));
                }
            }
            yearCombo.setSelectedIndex(0); // Reset selection
        });

        // Add date panel to the main frame
        gbc.gridx = 1;
        frame.add(datePanel, gbc);

        // Travel Purpose
        JLabel purposeLabel = new JLabel("Travel Purpose:");
        purposeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        purposeLabel.setForeground(Color.decode("#000080"));
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(purposeLabel, gbc);

        String[] purposes = {
            "Study", "Photography", "Work", "Medical Treatment", "Leisure",
            "Tourism", "Volunteering", "Visiting Family", "Research",
            "Study Abroad", "Physical Therapy", "Entertainment", "Other", "Additional"
        };
        JComboBox<String> purposeCombo = new JComboBox<>(purposes);
        gbc.gridx = 1;
        frame.add(purposeCombo, gbc);

        // Update visibility for custom budget input
        budgetCombo.addActionListener(e -> {
            customBudgetField.setVisible("Other".equals(budgetCombo.getSelectedItem()));
            frame.revalidate();
            frame.repaint();
        });

        // Adding a separator
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        frame.add(separator, gbc);
        gbc.gridwidth = 1;

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(80, 30)); // تعيين العرض إلى 80 بكسل والارتفاع إلى 30 بكسل
        nextButton.setBackground(Color.decode("#555555")); // لون رمادي داكن
        nextButton.setForeground(Color.WHITE); // لون النص أبيض
        gbc.gridx = 1; // وضع الزر في العمود الثاني
        gbc.gridy++; // الانتقال إلى الصف التالي

        frame.add(nextButton, gbc);

        // Button Action
        nextButton.addActionListener(e -> {
            boolean validInput = true;

            // Validate budget
            String selectedBudget = (String) budgetCombo.getSelectedItem();
            if (selectedBudget.trim().isEmpty() || ("Other".equals(selectedBudget) && customBudgetField.getText().trim().isEmpty())) {
                JOptionPane.showMessageDialog(frame, "Please enter a budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else if ("Other".equals(selectedBudget)) {
                try {
                    budget = Double.parseDouble(customBudgetField.getText());
                    if (budget <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid budget greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                }
            } else {
                budget = Double.parseDouble(selectedBudget.split("-")[0]);
            }

            // Validate number of travelers
            String selectedTravelers = (String) travelersCombo.getSelectedItem();
            if (selectedTravelers == null || selectedTravelers.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select the number of members.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else {
                numberOfTravelers = Integer.parseInt(selectedTravelers);
            }

            // Validate travel duration
            String selectedDuration = (String) durationCombo.getSelectedItem();
            if (selectedDuration == null || selectedDuration.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select the travel duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            } else {
                travelDuration = Integer.parseInt(selectedDuration);
                travelDurationUnit = (String) durationUnitCombo.getSelectedItem();
            }

            // Validate travel date
            travelDay = Integer.parseInt((String) dayCombo.getSelectedItem());
            travelMonth = Integer.parseInt((String) monthCombo.getSelectedItem());
            travelYear = Integer.parseInt((String) yearCombo.getSelectedItem());

            // Validate travel purpose
            travelPurpose = (String) purposeCombo.getSelectedItem();
            if (travelPurpose.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a travel purpose.", "Input Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
            }

            // If all inputs are valid, proceed to preferences
            if (validInput) {
                setPreference(0, String.valueOf(budget));
                setPreference(1, String.valueOf(numberOfTravelers));
                setPreference(2, String.valueOf(travelDuration));
                setPreference(3, travelDurationUnit);
                frame.dispose(); // Close the main info frame
                showPreferences(); // Show preferences window
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    public void showPreferences() {
        JFrame frame = new JFrame("Preferences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout(5, 5));

        // Main panel to hold form elements
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.decode("#f0f8ff"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

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
            {"Italian", "Chinese", "Indian", "Mexican", "American", "French", 
             "Japanese", "Thai", "Spanish", "Greek", "Lebanese", "Turkish", 
             "Vietnamese", "Korean", "Mediterranean", "Brazilian", "Caribbean"},
            {"Sightseeing", "Adventure", "Relaxation", "Cultural", "Food Tours", 
             "Shopping", "Beach Activities", "Sports", "Wildlife Safari", 
             "Cruises", "Wellness Retreats", "Historical Tours", "Road Trips"},
            {"Sunny", "Rainy", "Cold", "Mild", "Hot", "Humid", "Dry", 
             "Windy", "Snowy", "Foggy", "Pleasant"},
            {"Standard", "Luxury"},
            {"Standard", "Luxury"},
            {"Yes", "No"}
        };

        JComboBox<String>[] preferenceCombos = new JComboBox[questions.length];

        // Loop through the questions and add them to the form panel
        for (int i = 0; i < questions.length; i++) {
            final int index = i;
            JLabel questionLabel = new JLabel(questions[i]);
            questionLabel.setForeground(Color.decode("#000080"));
            preferenceCombos[i] = new JComboBox<>(options[i]);

            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(questionLabel, gbc);

            gbc.gridx = 1;
            formPanel.add(preferenceCombos[i], gbc);
        }

        // Country list
        JLabel countryLabel = new JLabel("Select Country:");
        countryLabel.setForeground(Color.decode("#000080"));
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
            boolean isVisible = "Yes".equals(preferenceCombos[5].getSelectedItem());
            countryLabel.setVisible(isVisible);
            countryCombo.setVisible(isVisible);
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
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.decode("#ff6347"));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            frame.dispose();
            showMainInfo(); // Go back to main info without losing answers
        });

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.setBackground(Color.decode("#32cd32"));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            // Handle submission logic here
            // You can process the preferences and travel plan
            frame.dispose();
        });

        // Adding buttons to a panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setBackground(Color.decode("#f0f8ff"));
        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        // Add form panel and button panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Questionaire q = new Questionaire();
            q.showMainInfo();
        });
    }
}