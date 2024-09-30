
package terhal;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TravelAppGUI {

    private static final Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static Questionaire questions = new Questionaire();
   // private static TravelPlan travel ;
        //private static boolean mainInfoCompleted = false;



    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(TravelAppGUI::showLoginOrRegister);  
    }

    private static void showLoginOrRegister() {
        JFrame frame = new JFrame("Login or Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));

        loginButton.addActionListener(e -> showLogin());
        registerButton.addActionListener(e -> showRegister());

        frame.add(loginButton);
        frame.add(registerButton);
        frame.setVisible(true);
    }
    
    private static void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLayout(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(10);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(144, 238, 144)); // أخضر فاتح
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        submitButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            // تحقق من تعبئة الحقول
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // تحقق من وجود المستخدم
            if (!users.containsKey(username)) {
                JOptionPane.showMessageDialog(frame, "User does not exist. Please register first.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // تحقق من صحة بيانات الدخول
            if (!isValidEmailFormat(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email format. Please enter a valid email address.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // تحقق من صحة بيانات الدخول
            if (!username.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(frame, "Username should contain only alphabetic characters.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // تحقق من صحة بيانات الدخول
            if (validateLogin(username, email, password)) {
                currentUser = users.get(username); // الحصول على المستخدم
                frame.dispose();
                showMainInfo();
                //travel = new TravelPlan(currentUser, questions);
                //showTravelPlan();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid login credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); 
        frame.add(submitButton);
        frame.setVisible(true);
    }
        
    private static void showRegister() {
    JFrame frame = new JFrame("Register");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(350, 300);
    frame.setLayout(new GridLayout(5, 2));

    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField(10);
    JLabel emailLabel = new JLabel("Email:");
    JTextField emailField = new JTextField(10);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(10);
    JButton submitButton = new JButton("Submit");

    submitButton.setBackground(new Color(144, 238, 144));
    submitButton.setForeground(Color.BLACK);
    submitButton.setFont(new Font("Arial", Font.BOLD, 16));

    submitButton.addActionListener(e -> {
        String username = userField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidEmailFormat(email)) {
            JOptionPane.showMessageDialog(frame, "Invalid email format. Please enter a valid email address.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
            JOptionPane.showMessageDialog(frame, "Email already registered.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!username.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(frame, "Username should contain only alphabetic characters.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        users.put(username, new User(username, email, password, 0, 0, 0, ""));
        JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); 
        frame.add(submitButton);
        frame.setVisible(true);
                
    }
    
        private static void showMainInfo() {
        questions.showMainInfo();
          //mainInfoCompleted = true;
  
    }

    private static boolean validateLogin(String username, String email, String password) {
        User user = users.get(username);
        return user != null && user.getEmail().equals(email) && user.getPassword().equals(password);
    }
    
    // Validate email format
    private static boolean isValidEmailFormat(String email) {
        return email.endsWith("hotmail.com") || email.endsWith("gmail.com");
    }
    
//       private static void showTravelPlan() {
//        if (mainInfoCompleted && currentUser != null) {
//            travel.showTravelPlan();
//        }
//    
//    }
}
