package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Flight extends JFrame {

    private Connection conn;
    private JFrame appWindow;
    private JPanel flightsPanel;
    private JComboBox<String> departureComboBox;
    private JComboBox<String> arrivalComboBox;
    String userId;

    public Flight(Connection connection, JFrame appWindow, String userId) {
        this.conn = connection;
        this.appWindow = appWindow;
        this.userId = userId;

        setTitle("Flight Information");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        String[] cities = {"Select", "Abha", "Dammam", "Hafar Al-Batin", "Jeddah", "Khamis Mushait", "Khobar", "Makkah", "Medina", "Najran", "Riyadh", "Tabuk"};
        departureComboBox = new JComboBox<>(cities);
        arrivalComboBox = new JComboBox<>(getCitiesinTPlan());

        departureComboBox.setPreferredSize(new Dimension(120, 25));
        arrivalComboBox.setPreferredSize(new Dimension(120, 25));
        departureComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        arrivalComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        departureComboBox.setBackground(Color.decode("#FFFFFF"));
        arrivalComboBox.setBackground(Color.decode("#FFFFFF"));
        departureComboBox.setForeground(Color.decode("#2E7D32"));
        arrivalComboBox.setForeground(Color.decode("#2E7D32"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#66BB6A"));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel departureLabel = new JLabel("Departure City: ");
        departureLabel.setForeground(Color.WHITE);
        departureLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(departureLabel);
        topPanel.add(departureComboBox);

        JLabel arrivalLabel = new JLabel("Arrival City: ");
        arrivalLabel.setForeground(Color.WHITE);
        arrivalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(arrivalLabel);
        topPanel.add(arrivalComboBox);

        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        flightsPanel = new JPanel();
        flightsPanel.setLayout(new BoxLayout(flightsPanel, BoxLayout.Y_AXIS));
        flightsPanel.setBackground(Color.WHITE);

        mainPanel.add(new JScrollPane(flightsPanel), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(Color.decode("#66BB6A"));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#388E3C"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#66BB6A"));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appWindow.setVisible(true);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(144, 238, 144));
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        departureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterFlights();
            }
        });

        arrivalComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterFlights();
            }
        });

        setVisible(true);
    }
    
        private String[] getCitiesinTPlan() {
    java.util.Set<String> citySet = new java.util.HashSet<>();
    java.util.List<String> cityList = new java.util.ArrayList<>();
    
    String query = "SELECT City FROM travelplans WHERE userId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            String city = rs.getString("City");
            if (citySet.add(city)) {
                cityList.add(city);
            }
        }  
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return cityList.toArray(new String[0]);
}
    

    private void filterFlights() {
        String departureCity = (String) departureComboBox.getSelectedItem();
        String arrivalCity = (String) arrivalComboBox.getSelectedItem();

        flightsPanel.removeAll();

        if (!"Select".equals(departureCity) && !"Select".equals(arrivalCity)) {
            loadFlightsFromDatabase(departureCity, arrivalCity);
        }

        flightsPanel.revalidate();
        flightsPanel.repaint();
    }

    private void loadFlightsFromDatabase(String departureCity, String arrivalCity) {
        String query = "SELECT airline, price, departure, arrival, duration FROM Flights WHERE departure = (SELECT airport_code FROM countries WHERE city = ?) AND arrival = (SELECT airport_code FROM countries WHERE city = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, departureCity);
            pstmt.setString(2, arrivalCity);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String airline = rs.getString("airline");
                double price = rs.getDouble("price");
                String departure = rs.getString("departure");
                String arrival = rs.getString("arrival");
                String duration = rs.getString("duration");

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
}
