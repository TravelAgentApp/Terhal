
package terhal;



import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class TravelAppGUI {
    private static final Map<String, User> users = new HashMap<>();
    private static User currentUser;

    private static String savedUsername = "";
    private static String savedPassword = "";
    private static String savedEmail = "";

    public static void main(String[] args) {
        readSavedCredentials(); // قراءة البيانات المحفوظة عند بدء التطبيق
        SwingUtilities.invokeLater(TravelAppGUI::showLoginOrRegister);
    }

    private static void showLoginOrRegister() {
        JFrame frame = new JFrame("Login or Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));

        loginButton.addActionListener(e -> showLogin());
        registerButton.addActionListener(e -> showRegister());

        frame.add(loginButton);
        frame.add(registerButton);
        frame.setVisible(true);
    }

    private static void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        userField.setText(savedUsername);
        userField.setToolTipText("Enter your username");

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);
        emailField.setText(savedEmail);
        emailField.setToolTipText("Enter your email");

        JLabel domainLabel = new JLabel("Domain:");
        String[] domains = {"gmail.com", "hotmail.com"};
        JComboBox<String> domainComboBox = new JComboBox<>(domains);
        domainComboBox.setToolTipText("Select your email domain");

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(emailField);
        emailPanel.add(domainComboBox);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setToolTipText("Enter your password (exactly 8 characters)");

        JCheckBox rememberMe = new JCheckBox("Remember Me");
        JButton submitButton = new JButton("Submit");

        rememberMe.setToolTipText("Save your login information");

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(userLabel, gbc);
        gbc.gridx = 1;
        frame.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        frame.add(emailPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(rememberMe, gbc);

        gbc.gridy = 4;
        frame.add(submitButton, gbc);

        submitButton.addActionListener(e -> handleLogin(frame, userField, emailField, passwordField, domainComboBox, rememberMe));

        frame.setVisible(true);
    }

    private static void handleLogin(JFrame frame, JTextField userField, JTextField emailField,
                                     JPasswordField passwordField, JComboBox<String> domainComboBox, JCheckBox rememberMe) {
        String username = userField.getText().trim();
        String email = emailField.getText().trim() + "@" + domainComboBox.getSelectedItem();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showErrorMessage(frame, "Please fill in all fields.");
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

        if (validateLogin(username, email, password)) {
            currentUser = users.get(username);
            if (rememberMe.isSelected()) {
                savedUsername = username;
                savedPassword = password;
                savedEmail = email;
                saveCredentials(username, email, password); // حفظ البيانات
            }
            frame.dispose();
            showMainInfo();
        } else {
            showErrorMessage(frame, "Invalid login credentials.");
        }
    }

    private static void saveCredentials(String username, String email, String password) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("credentials.txt"))) {
            writer.write(username + "\n" + email + "\n" + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readSavedCredentials() {
        try {
            Path path = Paths.get("credentials.txt");
            if (Files.exists(path)) {
                BufferedReader reader = Files.newBufferedReader(path);
                savedUsername = reader.readLine();
                savedEmail = reader.readLine();
                savedPassword = reader.readLine();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showRegister() {
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(15);
        firstNameField.setToolTipText("Enter your first name");

        JLabel secondNameLabel = new JLabel("Second Name:");
        JTextField secondNameField = new JTextField(15);
        secondNameField.setToolTipText("Enter your second name");

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(15);
        lastNameField.setToolTipText("Enter your last name");

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        userField.setToolTipText("Enter your username");

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);
        emailField.setToolTipText("Enter your email");

        JLabel domainLabel = new JLabel("Domain:");
        String[] domains = {"gmail.com", "hotmail.com"};
        JComboBox<String> domainComboBox = new JComboBox<>(domains);
        domainComboBox.setToolTipText("Select your email domain");

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(emailField);
        emailPanel.add(domainComboBox);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setToolTipText("Enter your password (exactly 8 characters)");

        JButton submitButton = new JButton("Submit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        frame.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(secondNameLabel, gbc);
        gbc.gridx = 1;
        frame.add(secondNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        frame.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(userLabel, gbc);
        gbc.gridx = 1;
        frame.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        frame.add(emailPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        gbc.gridy = 6;
        frame.add(submitButton, gbc);

        submitButton.addActionListener(e -> handleRegister(frame, firstNameField, secondNameField, lastNameField, userField, emailField, domainComboBox, passwordField));

        frame.setVisible(true);
    }

    private static void handleRegister(JFrame frame, JTextField firstNameField, JTextField secondNameField,
                                        JTextField lastNameField, JTextField userField,
                                        JTextField emailField, JComboBox<String> domainComboBox,
                                        JPasswordField passwordField) {
        String userId = String.valueOf(users.size() + 1);
        String firstName = firstNameField.getText().trim();
        String secondName = secondNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = userField.getText().trim();
        String email = emailField.getText().trim() + "@" + domainComboBox.getSelectedItem();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || secondName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showErrorMessage(frame, "Please fill in all fields.");
            return;
        }

        if (!firstName.matches("[a-zA-Z]+") || !secondName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            showErrorMessage(frame, "Names should contain only alphabetic characters.");
            return;
        }

        if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
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

        users.put(username, new User(userId, firstName + " " + secondName + " " + lastName, email, username, password));
        JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    private static void showErrorMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static boolean validateLogin(String username, String email, String password) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    private static void showMainInfo() {
        JOptionPane.showMessageDialog(null, "Welcome to the main interface, " + currentUser.getName() + "!", "Main Interface", JOptionPane.INFORMATION_MESSAGE);
        Questionaire questionaire = new Questionaire();
        questionaire.showMainInfo(); // فتح واجهة الاستبيان
    }
}









































































