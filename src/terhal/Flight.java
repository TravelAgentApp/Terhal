package terhal;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Flight extends JFrame {
    
    private Connection conn; 

    public Flight(Connection connection) {
        this.conn = connection; 

        // إعداد واجهة البرنامج
        setTitle("Flight Information");
        setSize(520, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // عنوان التطبيق
        JLabel titleLabel = new JLabel("Available Flights", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // لوحة لعرض الرحلات
        JPanel flightsPanel = new JPanel();
        flightsPanel.setLayout(new BoxLayout(flightsPanel, BoxLayout.Y_AXIS));
        flightsPanel.setBackground(Color.WHITE);


        loadFlightsFromDatabase(flightsPanel);


        add(new JScrollPane(flightsPanel), BorderLayout.CENTER);
        

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

    ImageIcon originalIcon = new ImageIcon("C:\\Users\\pc\\Desktop\\pngtree-airplane-silhouette-takeoff-state-png-image_6413666.jpg"); // استبدل هذا بالمسار الصحيح للصورة
    Image scaledImage = originalIcon.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH);
    iconLabel.setIcon(new ImageIcon(scaledImage));


    JLabel arrivalLabel = new JLabel(arrival, SwingConstants.CENTER);
    arrivalLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    JLabel durationLabel = new JLabel(duration, SwingConstants.CENTER);
    durationLabel.setFont(new Font("Arial", Font.ITALIC, 12));

    // ترتيب المكونات
    flightInfoPanel.add(departureLabel);
    flightInfoPanel.add(iconLabel);
    flightInfoPanel.add(arrivalLabel);
    flightInfoPanel.add(durationLabel);

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(flightInfoPanel, BorderLayout.CENTER);

    return panel;
}


    // كود لاختبار العرض
    public static void main(String[] args) throws SQLException {
        // يجب تمرير اتصال قاعدة البيانات هنا
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_app", "root", "123456"); 
        SwingUtilities.invokeLater(() -> new Flight(conn));
    }
}
