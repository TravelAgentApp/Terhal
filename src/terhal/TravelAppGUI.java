
package terhal;


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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import javax.swing.border.TitledBorder;

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
    String userId;

        // Connect to database
        public TravelAppGUI(Connection connection) {
            this.conn = connection; // Assign the provided connection from the Terhal2 class

            setTitle("Terhal Travel App");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            //showLoginOrRegister(); // Show login or register options
        }
        public TravelAppGUI(Connection connection, String userId) {
           this.userId = userId;
           this.conn = connection;
        }

        // Show the login or register options
       void showLoginOrRegister() {
        JFrame frame = new JFrame("Login or Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(520, 700);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // خلفية خفيفة

        // رسالة الترحيب
        JLabel welcomeLabel = new JLabel("Welcome To Terhal Application", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 100, 0)); // لون أخضر جذاب
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(70, 0, 20, 0));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // إضافة JLabel للصورة
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\pc\\Downloads\\d5210dc22bac2d626247c57b324ab1e6.jpg"); // استبدل بمسار الصورة الخاص بك
        Image scaledImage = originalIcon.getImage().getScaledInstance(520, 350, Image.SCALE_SMOOTH); // تغيير الحجم
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage)); // إنشاء JLabel بالصورة المعدلة
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // محاذاة الصورة في المنتصف
        frame.add(imageLabel, BorderLayout.CENTER); // إضافة الصورة المنتصف

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.setBackground(new Color(245, 245, 245)); // نفس الخلفية
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // هوامش للوحة

        // تعيين التباعد العمودي بين الصفوف
        ((GridLayout) buttonPanel.getLayout()).setVgap(15); // 15 بيكسل تباعد

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        styleButton(loginButton);
        styleButton(registerButton);

        loginButton.addActionListener(e -> {
            frame.dispose(); // Dispose of the login/register window
            showLogin(frame); // Show login
        });

        registerButton.addActionListener(e -> {
            frame.dispose(); // Dispose of the login/register window
            showRegister(frame); // Show registration
        });

        // إضافة الأزرار إلى اللوحة
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // مسافة فوق الصورة
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        frame.add(buttonPanel, BorderLayout.SOUTH); // إضافة الزر في الجزء السفلي
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE); // لون النص أبيض
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // حواف أكبر
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // مؤشر اليد عند المرور
        button.setOpaque(true);
    }

    private void showLogin(JFrame mainFrame) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setSize(520, 700);
        loginFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        userField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        passwordField = new JPasswordField(20);
        
        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(userLabel, gbc);
        gbc.gridx = 1;
        loginFrame.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFrame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginFrame.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> handleLogin(loginFrame, userField.getText(), new String(passwordField.getPassword()), mainFrame));

        loginFrame.getContentPane().setBackground(new Color(255, 255, 255));
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
        mainFrame.dispose();// Close the login/register window (passed as an argument)
        
        if (hasTravelPlan(currentUser.getUserId()))
        //showMainInfo();         // Call the main app window
        showPlansPage();
        else
          showMainInfo();   
    } else {
        JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials.");
    }
}

  // Method to check if user has a travel plan
    public boolean hasTravelPlan(String userId) {
        String query = "SELECT COUNT(*) FROM trips WHERE userId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showRegister(JFrame frame) {
    JFrame registerFrame = new JFrame("Register");
    registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    registerFrame.setSize(520, 700);
    registerFrame.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel firstNameLabel = new JLabel("First Name:");
    firstNameField = new JTextField(20);
    JLabel secondNameLabel = new JLabel("Second Name:");
    secondNameField = new JTextField(20);
    JLabel lastNameLabel = new JLabel("Last Name:");
    lastNameField = new JTextField(20);
    JLabel userLabel = new JLabel("Username:");
    userField = new JTextField(20);
    JLabel emailLabel = new JLabel("Email:");
    emailField = new JTextField(20);
    JLabel domainLabel = new JLabel("Domain:");
    String[] domains = {"@gmail.com", "@hotmail.com"};
    domainComboBox = new JComboBox<>(domains);
    JLabel passwordLabel = new JLabel("Password:");
    passwordField = new JPasswordField(20);
    JButton submitButton = new JButton("Submit");

    // Style the submit button
    styleButton(submitButton);

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
        //showMainInfo();
        showLoginOrRegister();
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
    
    public void showPlansPage(){
         // Launch the questionnaire to gather user preferences
        Questionaire Questionaire = new Questionaire(conn,currentUser.getUserId() );
        //call travel plan and take the userid as the constructor
        TravelPlan TravelPlan = new TravelPlan(Questionaire.getcityNames(currentUser.getUserId()),currentUser.getUserId(),Questionaire.getTripIdByUserId(currentUser.getUserId()), conn);
        App DisplayHome = new App (TravelPlan, Questionaire.getTripIdByUserId(currentUser.getUserId()));
        //TravelPlanner travelPlanner = new TravelPlanner(TravelPlan);
    }

      public void showPlansPageafterAddNew(){
         // Launch the questionnaire to gather user preferences
        Questionaire Questionaire = new Questionaire(conn,userId );
        //call travel plan and take the userid as the constructor
        TravelPlan TravelPlan = new TravelPlan(Questionaire.getcityNames(userId),userId,Questionaire.getTripIdByUserId(userId), conn);
        App DisplayHome = new App (TravelPlan, Questionaire.getTripIdByUserId(userId));
        //TravelPlanner travelPlanner = new TravelPlanner(TravelPlan);
    }


public class App extends JFrame {
    private JPanel cityPanel;
    private JFrame mainFrame;
    private Map<String, List<DailyPlan>> cityDailyPlans; // Store plans for each city separately
    TravelPlan travelPlan;
    int tripId;

public App(TravelPlan travelPlan, int tripId) {
    this.travelPlan = travelPlan;
    this.tripId = tripId;
    this.cityDailyPlans = new HashMap<>();
    setTitle("Welcome to Terhal");
    setSize(520, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    // Program icon
    setIconImage(Toolkit.getDefaultToolkit().getImage("path/to/icon.png"));

    // Create a content panel with a border
    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 179, 113), 2)); // Adding a lightweight border
    contentPanel.setLayout(new BorderLayout());

    // Title Label
    JLabel titleLabel = new JLabel("Travel Plans", JLabel.CENTER);
    titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 27)); // Dark font
    titleLabel.setForeground(new Color(34, 139, 34)); // Dark green color
    contentPanel.add(titleLabel, BorderLayout.NORTH); // Add title to content panel

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
    contentPanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to content panel
    add(contentPanel, BorderLayout.CENTER); // Add content panel to the frame

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(144, 238, 144));

    // "Add New Plan" button
    JButton hotelL = new JButton("Hotel");
    JButton addNewPlanButton = new JButton("Add New Plan");
    JButton FlightL = new JButton("Flight");

    // Style the button using the previously defined method
    styleButton(addNewPlanButton);
    addNewPlanButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showMainInfo();
            dispose();
        }
    });
        styleButton(hotelL);
        
        hotelL.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel hotelWindow = new Hotel(conn, App.this); // تمرير مرجع النافذة الحالية
        setVisible(false); // إخفاء نافذة App
        dispose();

    }
});
            styleButton(FlightL);
    FlightL.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        Flight a =new Flight();
            dispose();
        }
    });
    // Add buttons to the panel
    buttonPanel.add(FlightL);
    buttonPanel.add(addNewPlanButton);

    buttonPanel.add(hotelL);

    // Add button panel to the bottom of the frame
    add(buttonPanel, BorderLayout.SOUTH);


    setVisible(true);
}

private JPanel createCityPanel(String cityName) {
    JPanel panel = new JPanel(new BorderLayout());

    // Create a clickable JLabel for the city name
    JLabel cityLabel = new JLabel(cityName, JLabel.CENTER);
    cityLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    cityLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            initializePlansForCity(cityName);
            showCityDays(cityName);
        }
    });

    JLabel cityImageLabel = new JLabel("", JLabel.CENTER);
    String imagePath = getCityImagePath(cityName);
    ImageIcon cityIcon = resizeImage(imagePath, 400, 250);

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
        case "Abha": return "src/Abha.jpg";
        case "Dammam": return "src/Dammam.jpg";
        case "Hafar Al-Batin": return "src/HafarAlBatin.jpg";
        case "Jeddah": return "src/Jeddah.jpg";
        case "Khamis Mushait": return "src/KhamisMushait.jpg";
        case "Khobar": return "src/Khobar.jpg";
        case "Medina": return "src/Medina.jpg";
        case "Najran": return "src/Najran.jpg";
        case "Riyadh": return "src/Riyadh.jpg";
        case "Tabuk": return "src/Tabuk.jpg";
        case "Makkah": return "src/Makkah.jpg";
        default: return null;
    }
}

private ImageIcon resizeImage(String imagePath, int width, int height) {
    ImageIcon icon = new ImageIcon(imagePath);
    if (icon.getIconWidth() == -1) return null;
    Image img = icon.getImage();
    Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(newImg);
}





    // Initialize plans for a specific city
    public void initializePlansForCity(String city) {
        int travelDuration = travelPlan.getduration(tripId, userId);

        // Retrieve activities and restaurants for the city
        Map<String, List<Integer>> activitiesByTime = travelPlan.getActivitiesByTime(city);
        List<Integer> restaurants = travelPlan.getRestaurantsByCity(city);

        // Debugging output to verify correct data retrieval
        System.out.println("City: " + city);
        System.out.println("Activities by time: " + activitiesByTime);
        System.out.println("Restaurants: " + restaurants);

        // Create the daily itinerary for the city
        List<Day> itinerary = travelPlan.createDailyItinerary(activitiesByTime, restaurants, travelDuration);

        List<DailyPlan> dailyPlans = new ArrayList<>();
        for (Day day : itinerary) {
            DailyPlan dailyPlan = new DailyPlan(
                day.getDayName(),
                city,
                day.getMorningActivity(),
                day.getEveningActivity(),
                day.getNightActivity(),
                day.getRestaurantBreakfast(),
                day.getRestaurantDinner(),
                mainFrame
            );
            dailyPlans.add(dailyPlan);
        }

        cityDailyPlans.put(city, dailyPlans);
    }

    // Show buttons for each day of the selected city
    // Show buttons for each day of the selected city
public void showCityDays(String cityName) {
    if (mainFrame != null) {
        mainFrame.dispose();
    }

    mainFrame = new JFrame(cityName + " - Daily Plans");
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainFrame.setSize(520, 700); // Matching style and size
    mainFrame.setLayout(new BorderLayout());
    mainFrame.getContentPane().setBackground(new Color(220, 255, 220));


    JLabel headerLabel = new JLabel("Daily Plans for " + cityName);
    headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
    headerLabel.setForeground(new Color(34, 139, 34));
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    mainFrame.add(headerLabel, BorderLayout.NORTH);

    List<DailyPlan> dailyPlans = cityDailyPlans.get(cityName);
    JPanel planPanel = new JPanel(new GridLayout(0, 1));
    planPanel.setBackground(new Color(220, 255, 220));

    for (DailyPlan dailyPlan : dailyPlans) {
        JButton dayButton = new JButton(dailyPlan.getDayName());
        dayButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        dayButton.setBackground(new Color(60, 179, 113));
        dayButton.setForeground(Color.WHITE);
        dayButton.setFocusPainted(false);
        dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dayButton.addActionListener(e -> showDailyPlan(dailyPlan));
        planPanel.add(dayButton);
    }

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    buttonPanel.setBackground(new Color(220, 255, 220));
    JButton backButton = new JButton("Back");
    styleButton(backButton);
    backButton.addActionListener(e -> mainFrame.dispose());
    buttonPanel.add(backButton);

    mainFrame.add(planPanel, BorderLayout.CENTER);
    mainFrame.add(buttonPanel, BorderLayout.SOUTH);

    mainFrame.setVisible(true);
}

private void showDailyPlan(DailyPlan dailyPlan) {
    if (dailyPlan != null) {
        dailyPlan.showDetails();
    }
}

public class DailyPlan {
    private String day;
    private String city;
    private Integer morningActivity;
    private Integer afternoonActivity;
    private Integer nightActivity;
    private Integer breakfastRestaurant;
    private Integer dinnerRestaurant;
    private JFrame mainFrame;
    private JFrame parentFrame;

    public DailyPlan(String day, String city, Integer morningActivity, Integer afternoonActivity,
                     Integer nightActivity, Integer breakfastRestaurant, Integer dinnerRestaurant,
                     JFrame parentFrame) {
        this.day = day;
        this.city = city;
        this.morningActivity = morningActivity;
        this.afternoonActivity = afternoonActivity;
        this.nightActivity = nightActivity;
        this.breakfastRestaurant = breakfastRestaurant;
        this.dinnerRestaurant = dinnerRestaurant;
        this.parentFrame = parentFrame;
    }

    public String getDayName() {
        return day;
    }

    public void showDetails() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        mainFrame = new JFrame(day + " Plan in " + city);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(520, 700);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(220, 255, 220));
        
        JLabel headerLabel = new JLabel("Plan for " + day + " in " + city);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        mainFrame.add(headerLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setBackground(new Color(220, 255, 220));
        detailsPanel.add(new JLabel("Morning Activity: " + getActivityName(morningActivity)));
        detailsPanel.add(new JLabel("Afternoon Activity: " + getActivityName(afternoonActivity)));
        detailsPanel.add(new JLabel("Night Activity: " + getActivityName(nightActivity)));
        detailsPanel.add(new JLabel("Breakfast Restaurant: " + getRestaurantName(breakfastRestaurant)));
        detailsPanel.add(new JLabel("Dinner Restaurant: " + getRestaurantName(dinnerRestaurant)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setBackground(new Color(220, 255, 220));
        JButton editButton = new JButton("Edit Plan");
        JButton saveButton = new JButton("Save Plan");
        JButton backButton = new JButton("Back");
        
        styleButton(editButton);
        styleButton(saveButton);
        styleButton(backButton);

        editButton.addActionListener(e -> showEditDialog());
        saveButton.addActionListener(e -> savePlanToFile());
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            parentFrame.setVisible(true);
        });

        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Add buttons for more activity and restaurant details
        JPanel detailsButtonPanel = new JPanel(new GridLayout(5, 2));
        detailsButtonPanel.setBackground(new Color(220, 255, 220));
        addDetailsButton(detailsButtonPanel, "Morning Activity Details", morningActivity);
        addDetailsButton(detailsButtonPanel, "Afternoon Activity Details", afternoonActivity);
        addDetailsButton(detailsButtonPanel, "Night Activity Details", nightActivity);
        addDetailsButton(detailsButtonPanel, "Breakfast Restaurant Details", breakfastRestaurant);
        addDetailsButton(detailsButtonPanel, "Dinner Restaurant Details", dinnerRestaurant);

        mainFrame.add(detailsPanel, BorderLayout.CENTER);
        mainFrame.add(detailsButtonPanel, BorderLayout.SOUTH);
        mainFrame.add(buttonPanel, BorderLayout.NORTH);

        mainFrame.setVisible(true);
    }

    private void addDetailsButton(JPanel panel, String buttonText, Integer id) {
        if (id != null) {
            JButton detailsButton = new JButton(buttonText);
            styleButton(detailsButton);
            detailsButton.addActionListener(e -> showDetailsDialog(id));
            panel.add(detailsButton);
        }
    }

    private void showDetailsDialog(int id) {
        String details = "";
        String title = "Details";

        if (travelPlan.isActivityId(id)) {
            details = travelPlan.getActivityDetailsById(id);
            title = "Activity Details";
        } else if (travelPlan.isRestaurantId(id)) {
            details = travelPlan.getRestaurantDetailsById(id);
            title = "Restaurant Details";
        } else {
            details = "No details available.";
        }

        JOptionPane.showMessageDialog(mainFrame, details, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private String getActivityName(Integer activityId) {
        if (activityId == null) return "None";
        return travelPlan.getActivityNameById(activityId);
    }

    private String getRestaurantName(Integer restaurantId) {
        if (restaurantId == null) return "None";
        return travelPlan.getRestaurantNameById(restaurantId);
    }

    private void showEditDialog() {
        JFrame editFrame = new JFrame("Edit " + day + " Plan");
        editFrame.setSize(520, 700);
        editFrame.setLayout(new GridLayout(6, 2));
        editFrame.getContentPane().setBackground(new Color(220, 255, 220));
        //editFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(34, 139, 34)));

        // Get activity names for the city
        List<String> activityNames = travelPlan.getActivityNamesByCityId(travelPlan.getCitytIdByName(city));
        String[] activityOptions = activityNames.toArray(new String[0]);

        JComboBox<String> morningActivityCombo = new JComboBox<>(activityOptions);
        JComboBox<String> afternoonActivityCombo = new JComboBox<>(activityOptions);
        JComboBox<String> nightActivityCombo = new JComboBox<>(activityOptions);

        morningActivityCombo.setSelectedItem(getActivityName(morningActivity));
        afternoonActivityCombo.setSelectedItem(getActivityName(afternoonActivity));
        nightActivityCombo.setSelectedItem(getActivityName(nightActivity));

        List<String> restaurantNames = travelPlan.getRestaurantNamesByCity(city);
        String[] restaurantOptions = restaurantNames.toArray(new String[0]);

        JComboBox<String> breakfastRestaurantCombo = new JComboBox<>(restaurantOptions);
        JComboBox<String> dinnerRestaurantCombo = new JComboBox<>(restaurantOptions);

        breakfastRestaurantCombo.setSelectedItem(getRestaurantName(breakfastRestaurant));
        dinnerRestaurantCombo.setSelectedItem(getRestaurantName(dinnerRestaurant));

        editFrame.add(new JLabel("Morning Activity:"));
        editFrame.add(morningActivityCombo);
        editFrame.add(new JLabel("Afternoon Activity:"));
        editFrame.add(afternoonActivityCombo);
        editFrame.add(new JLabel("Night Activity:"));
        editFrame.add(nightActivityCombo);
        editFrame.add(new JLabel("Breakfast Restaurant:"));
        editFrame.add(breakfastRestaurantCombo);
        editFrame.add(new JLabel("Dinner Restaurant:"));
        editFrame.add(dinnerRestaurantCombo);

        JButton saveButton = new JButton("Save Changes");
        styleButton(saveButton);
        saveButton.addActionListener(e -> {
            morningActivity = travelPlan.getActivityIdByName((String) morningActivityCombo.getSelectedItem());
            afternoonActivity = travelPlan.getActivityIdByName((String) afternoonActivityCombo.getSelectedItem());
            nightActivity = travelPlan.getActivityIdByName((String) nightActivityCombo.getSelectedItem());
            breakfastRestaurant = travelPlan.getRestaurantIdByName((String) breakfastRestaurantCombo.getSelectedItem());
            dinnerRestaurant = travelPlan.getRestaurantIdByName((String) dinnerRestaurantCombo.getSelectedItem());

            editFrame.dispose();
            showDetails();
        });

        editFrame.add(saveButton);
        editFrame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
