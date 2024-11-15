
package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Hotel extends JFrame {

    private Connection conn; // الاتصال بقاعدة البيانات

    public Hotel(Connection connection) {
        this.conn = connection; // ربط الاتصال

        // إعداد الإطار الرئيسي
        JFrame frame = new JFrame("Hotel Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(750, 900);
                frame.setSize(600, 700);

        frame.setResizable(true);

        // إعداد اللوحة الرئيسية
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F4F9F4")); // خلفية خضراء فاتحة

        // إعداد شريط القوائم لاختيار المدينة
        String[] cities = {"Select", "Abha", "Dammam", "Hafar Al-Batin", "Jeddah", "Khamis Mushait", "Khobar", "Makkah", "Medina", "Najran", "Riyadh", "Tabuk"};
        JComboBox<String> cityComboBox = new JComboBox<>(cities);
        cityComboBox.setPreferredSize(new Dimension(300, 40));
        cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        cityComboBox.setBackground(Color.decode("#FFFFFF"));
        cityComboBox.setForeground(Color.decode("#2E7D32"));

        // إعداد اللوحة لعرض الفنادق
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new BoxLayout(hotelPanel, BoxLayout.Y_AXIS));
        hotelPanel.setBackground(Color.decode("#F1F8F1"));//الخلفية خضراء 
        hotelPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // إعداد اللوحة العلوية
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        topPanel.setBackground(Color.decode("#66BB6A"));//الي فوق
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel selectLabel = new JLabel("Select City: ");
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(selectLabel);
        topPanel.add(cityComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(hotelPanel), BorderLayout.CENTER);

        // إضافة وظيفة لتصفية الفنادق بناءً على المدينة المختارة
        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCity = (String) cityComboBox.getSelectedItem();
                hotelPanel.removeAll(); // إزالة جميع البطاقات الحالية

                if (!selectedCity.equals("Select")) {
                    // جلب الفنادق من قاعدة البيانات للمدينة المحددة
                    loadHotelsFromDatabase(selectedCity, hotelPanel);
                }

                hotelPanel.revalidate(); // تحديث اللوحة
                hotelPanel.repaint(); // إعادة رسم اللوحة
            }
        });

        // عرض الإطار
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // طريقة لجلب الفنادق من قاعدة البيانات للمدينة المحددة
    private void loadHotelsFromDatabase(String city, JPanel hotelPanel) {
        String query = "SELECT * FROM Hotels WHERE location = ?"; // استعلام للحصول على الفنادق في المدينة المحددة
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, city); // تعيين المدينة كمعامل
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // الحصول على بيانات الفندق من النتيجة
                String name = rs.getString("name");
                String location = rs.getString("location");
                double price_per_night = rs.getDouble("price_per_night");
                String hotel_class = rs.getString("hotel_class");
                double rating = rs.getDouble("rating");
                String amenities = rs.getString("amenities");

                // إضافة بطاقة الفندق إلى اللوحة
                hotelPanel.add(createHotelCard(name, location, price_per_night, hotel_class, rating, amenities));
                hotelPanel.add(Box.createVerticalStrut(20)); // إضافة مسافة بين الفنادق
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // طريقة لإنشاء بطاقة الفندق
    private JPanel createHotelCard(String name, String location, double price, String hotelClassValue, double rating, String amenities) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#388E3C"), 2, true), // أخضر داكن
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(650, 180));
        card.setBackground(Color.WHITE);

        // إعداد مسار الصورة
        String imagePath = "/imageout/" + name + ".jpg";  // يفترض أن الصورة تحمل اسم الفندق مع امتداد .jpg

        // تحميل الصورة باستخدام getResource
        URL imageURL = Hotel.class.getResource(imagePath);
        if (imageURL != null) {
            // تحميل الصورة من URL
            ImageIcon hotelImage = new ImageIcon(imageURL);
            Image img = hotelImage.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH);
            hotelImage = new ImageIcon(img);

            // إنشاء JLabel للصورة
            JLabel imageLabel = new JLabel(hotelImage);
            imageLabel.setPreferredSize(new Dimension(170, 170));
            card.add(imageLabel); // إضافة الصورة إلى البطاقة
        } else {
            // في حالة عدم وجود الصورة، إظهار عنصر نائب
            JLabel placeholder = new JLabel("Image Unavailable", SwingConstants.CENTER);
            placeholder.setPreferredSize(new Dimension(170, 170));
            placeholder.setBackground(Color.decode("#C8E6C9")); // لون أخضر فاتح
            placeholder.setOpaque(true);
            card.add(placeholder);
        }

        // إعداد النصوص
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel hotelName = new JLabel(name);
        hotelName.setFont(new Font("Arial", Font.BOLD, 18));
        hotelName.setAlignmentX(Component.LEFT_ALIGNMENT);
        hotelName.setForeground(Color.decode("#388E3C"));

        JLabel hotelLocation = new JLabel("Location: " + location);
        hotelLocation.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelLocation.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hotelPrice = new JLabel("Price: " + price + " SAR / night");
        hotelPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelPrice.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hotelClassLabel = new JLabel("Class: " + hotelClassValue);
        hotelClassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelClassLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hotelRating = new JLabel("Rating: " + rating);
        hotelRating.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelRating.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hotelAmenities = new JLabel("Amenities: " + amenities);
        hotelAmenities.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelAmenities.setAlignmentX(Component.LEFT_ALIGNMENT);

        // إضافة النصوص إلى اللوحة
        textPanel.add(hotelName);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(hotelLocation);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(hotelPrice);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(hotelClassLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(hotelRating);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(hotelAmenities);

        // إضافة اللوحة النصية إلى البطاقة
        card.add(Box.createHorizontalStrut(20)); // مسافة بين الصورة والنص
        card.add(textPanel);

        return card;
    }
}

