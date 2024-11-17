package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Flight extends JFrame {

    private Connection conn;
    private JFrame appWindow;

    public Flight(Connection connection, JFrame appWindow) {
        this.conn = connection;
        this.appWindow = appWindow;

        // إعداد واجهة البرنامج
        setTitle("Flight Information");
        setSize(520, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // إنشاء اللوحة الرئيسية
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // إعداد شريط القوائم لاختيار المدينة
        String[] cities = {"Select", "Abha", "Dammam", "Hafar Al-Batin", "Jeddah", "Khamis Mushait", "Khobar", "Makkah", "Medina", "Najran", "Riyadh", "Tabuk"};
        JComboBox<String> cityComboBox = new JComboBox<>(cities);
        cityComboBox.setPreferredSize(new Dimension(300, 40));
        cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        cityComboBox.setBackground(Color.decode("#FFFFFF"));
        cityComboBox.setForeground(Color.decode("#2E7D32"));

        // إعداد اللوحة العلوية
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        topPanel.setBackground(Color.decode("#66BB6A")); // لون خلفية للوحة العلوية
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel selectLabel = new JLabel("Select City: ");
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(selectLabel);
        topPanel.add(cityComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // لوحة لعرض الرحلات
        JPanel flightsPanel = new JPanel();
        flightsPanel.setLayout(new BoxLayout(flightsPanel, BoxLayout.Y_AXIS));
        flightsPanel.setBackground(Color.WHITE);

        loadFlightsFromDatabase(flightsPanel);

        mainPanel.add(new JScrollPane(flightsPanel), BorderLayout.CENTER);

        // إعداد زر "رجوع" ووضعه أسفل الواجهة
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(Color.decode("#66BB6A"));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);

        // إضافة تأثيرات التمرير على الزر
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#388E3C"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#66BB6A"));
            }
        });

        // إضافة مستمع الأحداث للزر
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appWindow.setVisible(true);
                dispose();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(144, 238, 144));
        buttonPanel.add(backButton);

        // إضافة زر "رجوع" إلى الجزء السفلي من الواجهة
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadFlightsFromDatabase(JPanel flightsPanel) {
        String query = "SELECT airline, price, departure, arrival, duration FROM Flights"; // استعلام عن بيانات الرحلات
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String airline = rs.getString("airline");
                double price = rs.getDouble("price");
                String departure = rs.getString("departure");
                String arrival = rs.getString("arrival");
                String duration = rs.getString("duration");

                // إنشاء بطاقة عرض لكل رحلة
                flightsPanel.add(createFlightPanel(airline, price, departure, arrival, duration));
                flightsPanel.add(Box.createVerticalStrut(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel createFlightPanel(String airline, double price, String departure, String arrival, String duration) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.decode("#F1F8F1"));
        panel.setPreferredSize(new Dimension(480, 80));
        panel.setMaximumSize(new Dimension(480, 80));

        // العنوان والسعر
        JLabel airlineLabel = new JLabel(airline, SwingConstants.LEFT);
        airlineLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("$" + price, SwingConstants.RIGHT);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(airlineLabel, BorderLayout.WEST);
        headerPanel.add(priceLabel, BorderLayout.EAST);

        JPanel flightInfoPanel = new JPanel(new GridLayout(1, 5));
        flightInfoPanel.setOpaque(false);

        JLabel departureLabel = new JLabel(departure, SwingConstants.CENTER);
        departureLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalIcon = new ImageIcon("C:\\Users\\pc\\Desktop\\pngtree-airplane-silhouette-takeoff-state-png-image_6413666.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));

        JLabel arrivalLabel = new JLabel(arrival, SwingConstants.CENTER);
        arrivalLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel durationLabel = new JLabel(duration, SwingConstants.CENTER);
        durationLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        flightInfoPanel.add(departureLabel);
        flightInfoPanel.add(iconLabel);
        flightInfoPanel.add(arrivalLabel);
        flightInfoPanel.add(durationLabel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(flightInfoPanel, BorderLayout.CENTER);

        return panel;
    }

//    // كود لاختبار العرض
//    public static void main(String[] args) throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_app", "root", "123456");
//        SwingUtilities.invokeLater(() -> new Flight(conn, null));
//    }
}