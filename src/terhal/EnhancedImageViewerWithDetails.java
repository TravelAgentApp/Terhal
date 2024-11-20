package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.*;

public class EnhancedImageViewerWithDetails {
    private JFrame frame;
    private JLabel imageLabel;
    private JPanel commentPanel;
    private JButton likeButton, dislikeButton, addCommentButton, nextButton, prevButton, uploadButton, viewPlanButton;
    private JLabel likeCountLabel, dislikeCountLabel;
    private JLabel totalLikesLabel, totalDislikesLabel; // عدادات إجمالية
    private int currentIndex = 0;
    private String currentUserId; // المستخدم الحالي
    private ArrayList<Integer> imageIds = new ArrayList<>(); // قائمة بمعرفات الصور من قاعدة البيانات
    Connection conn;
    private JFrame appWindow; // مرجع للإطار السابق (App)

    public EnhancedImageViewerWithDetails(String userId, Connection conn, JFrame appWindow) {
        this.currentUserId = userId;
        this.conn = conn;
        this.appWindow = appWindow;

        if (currentUserId == null || currentUserId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You must log in to continue.");
            System.exit(0); // إنهاء البرنامج إذا لم يتم تسجيل الدخول
        }

        frame = new JFrame("Explore All Experiences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(34, 49, 63));

        // عرض الصورة
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // لوحة التعليقات
        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBackground(new Color(220, 237, 200));
        commentPanel.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));

        // الأزرار
        likeButton = new JButton("❤️ Like");
        dislikeButton = new JButton("👎 Dislike");
        addCommentButton = new JButton("Add Comment");
        uploadButton = new JButton("Upload Experience");
        viewPlanButton = new JButton("View Plan");

        likeCountLabel = new JLabel("Likes: 0");
        dislikeCountLabel = new JLabel("Dislikes: 0");

        totalLikesLabel = new JLabel("0");
        totalDislikesLabel = new JLabel("0");

        // ضبط الألوان والخطوط
        totalLikesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLikesLabel.setForeground(Color.WHITE);
        totalDislikesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalDislikesLabel.setForeground(Color.WHITE);

        likeButton.setBackground(new Color(200, 230, 201));
        dislikeButton.setBackground(new Color(200, 230, 201));
        addCommentButton.setBackground(new Color(76, 175, 80));
        addCommentButton.setForeground(Color.WHITE);
        uploadButton.setBackground(new Color(76, 175, 80));
        uploadButton.setForeground(Color.WHITE);
        viewPlanButton.setBackground(new Color(76, 175, 80));
        viewPlanButton.setForeground(Color.WHITE);

        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        nextButton.setBackground(new Color(76, 175, 80));
        prevButton.setBackground(new Color(76, 175, 80));
        nextButton.setForeground(Color.WHITE);
        prevButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(76, 175, 80));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(e -> {
            appWindow.setVisible(true);
            frame.dispose();
        });

        // الجزء السفلي للأزرار والإحصائيات
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 4, 5, 5));
        bottomPanel.setBackground(new Color(34, 49, 63));

        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(addCommentButton);
        bottomPanel.add(uploadButton);

        bottomPanel.add(likeButton);
        bottomPanel.add(dislikeButton);
        bottomPanel.add(likeCountLabel);
        bottomPanel.add(dislikeCountLabel);

        bottomPanel.add(viewPlanButton);
        bottomPanel.add(backButton);

        // عدادات اللايك والديسلايك الإجمالية
        bottomPanel.add(new JLabel("Total Likes:", SwingConstants.RIGHT));
        bottomPanel.add(totalLikesLabel);
        bottomPanel.add(new JLabel("Total Dislikes:", SwingConstants.RIGHT));
        bottomPanel.add(totalDislikesLabel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(34, 49, 63));
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        JScrollPane commentScrollPane = new JScrollPane(commentPanel);
        commentScrollPane.setPreferredSize(new Dimension(200, 0));
        contentPanel.add(commentScrollPane, BorderLayout.WEST);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(contentPanel, BorderLayout.CENTER);

        loadImagesFromDatabase();
        updateDisplay();
        updateTotalStats();

        likeButton.addActionListener(e -> toggleLike());
        dislikeButton.addActionListener(e -> toggleDislike());
        addCommentButton.addActionListener(e -> addNewComment());
        uploadButton.addActionListener(e -> uploadNewExperience());
        nextButton.addActionListener(e -> navigateImages(1));
        prevButton.addActionListener(e -> navigateImages(-1));
        viewPlanButton.addActionListener(e -> viewPlanForUploader());

        frame.setVisible(true);
    }

    private void loadImagesFromDatabase() {
        imageIds.clear();
        try {
            String query = "SELECT imageId FROM user_images";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                imageIds.add(resultSet.getInt("imageId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load images from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDisplay() {
    if (imageIds.isEmpty()) {
        imageLabel.setIcon(null);
        JOptionPane.showMessageDialog(frame, "No images to display!");
        return;
    }

    int imageId = imageIds.get(currentIndex);

    try {
        // **جلب بيانات الصورة واللايكات والديسلايكات**
        String imageQuery = "SELECT imageData, likes, dislikes FROM user_images WHERE imageId = ?";
        PreparedStatement imageStatement = conn.prepareStatement(imageQuery);
        imageStatement.setInt(1, imageId);

        ResultSet imageResultSet = imageStatement.executeQuery();
        if (imageResultSet.next()) {
            byte[] imageData = imageResultSet.getBytes("imageData");
            int likes = imageResultSet.getInt("likes");
            int dislikes = imageResultSet.getInt("dislikes");

            // **عرض الصورة**
            ImageIcon icon = new ImageIcon(imageData);
            int width = frame.getWidth();
            int height = frame.getHeight() - 200;
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));

            // **عرض عدد اللايكات والديسلايكات**
            likeCountLabel.setText("Likes: " + likes);
            dislikeCountLabel.setText("Dislikes: " + dislikes);
        }

        // **جلب التعليقات**
        String commentQuery = """
            SELECT u.username, c.commentText, c.timestamp 
            FROM comments c 
            JOIN users u ON c.userId = u.userId 
            WHERE c.imageId = ?
        """;
        PreparedStatement commentStatement = conn.prepareStatement(commentQuery);
        commentStatement.setInt(1, imageId);

        ResultSet commentResultSet = commentStatement.executeQuery();
        commentPanel.removeAll(); // إزالة التعليقات السابقة لعرض الجديدة

        if (!commentResultSet.isBeforeFirst()) { // **إذا لم توجد تعليقات**
            JLabel noComments = new JLabel("No comments yet!", SwingConstants.CENTER);
            noComments.setFont(new Font("Arial", Font.ITALIC, 12));
            noComments.setForeground(Color.GRAY);
            commentPanel.add(noComments);
        } else {
            // **عرض كل تعليق**
            while (commentResultSet.next()) {
                String username = commentResultSet.getString("username");
                String text = commentResultSet.getString("commentText");
                LocalDateTime timestamp = commentResultSet.getTimestamp("timestamp").toLocalDateTime();

                // **صندوق لكل تعليق**
                JPanel commentBox = new JPanel();
                commentBox.setLayout(new BorderLayout());
                commentBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                commentBox.setBackground(Color.WHITE);
                commentBox.setMaximumSize(new Dimension(400, 50));

                // **اسم المستخدم**
                JLabel userLabel = new JLabel(username + ":");
                userLabel.setFont(new Font("Arial", Font.BOLD, 12));
                userLabel.setForeground(new Color(0, 102, 204));

                // **النص**
                JLabel commentTextLabel = new JLabel("<html>" + text + "</html>");
                commentTextLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                commentTextLabel.setForeground(Color.BLACK);

                // **الوقت**
                JLabel timestampLabel = new JLabel(timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), SwingConstants.RIGHT);
                timestampLabel.setFont(new Font("Arial", Font.ITALIC, 10));
                timestampLabel.setForeground(Color.GRAY);

                commentBox.add(userLabel, BorderLayout.NORTH);
                commentBox.add(commentTextLabel, BorderLayout.CENTER);
                commentBox.add(timestampLabel, BorderLayout.SOUTH);

                commentPanel.add(commentBox); // إضافة التعليق إلى اللوحة
            }
        }

        commentPanel.revalidate(); // تحديث لوحة التعليقات
        commentPanel.repaint();

        // **تحديث العدادات الإجمالية**
        updateTotalStats();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Failed to load data from database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void updateTotalStats() {
        try {
            String query = "SELECT SUM(likes) AS totalLikes, SUM(dislikes) AS totalDislikes FROM user_images";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalLikesLabel.setText(String.valueOf(resultSet.getInt("totalLikes")));
                totalDislikesLabel.setText(String.valueOf(resultSet.getInt("totalDislikes")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void toggleLike() {
        if (imageIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No image selected for liking.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int imageId = imageIds.get(currentIndex);

        try {
            String query = "UPDATE user_images SET likes = likes + 1 WHERE imageId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, imageId);
            statement.executeUpdate();

            updateDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to like the image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleDislike() {
        if (imageIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No image selected for disliking.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int imageId = imageIds.get(currentIndex);

        try {
            String query = "UPDATE user_images SET dislikes = dislikes + 1 WHERE imageId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, imageId);
            statement.executeUpdate();

            updateDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to dislike the image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void navigateImages(int step) {
        if (imageIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No images available to navigate!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // تحديث الفهرس الحالي بناءً على الخطوة مع ضمان البقاء داخل حدود القائمة
        currentIndex = (currentIndex + step + imageIds.size()) % imageIds.size();

        // تحديث العرض بناءً على الصورة الحالية
        updateDisplay();
    }

    private void addNewComment() {
        if (imageIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No image selected for commenting.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int imageId = imageIds.get(currentIndex);
        String commentText = JOptionPane.showInputDialog(frame, "Enter your comment:");

        if (commentText != null && !commentText.trim().isEmpty()) {
            try {
                String query = "INSERT INTO comments (imageId, userId, commentText, timestamp) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, imageId);
                statement.setString(2, currentUserId);
                statement.setString(3, commentText.trim());
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

                statement.executeUpdate();
                updateDisplay();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to add comment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void uploadNewExperience() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                String query = "INSERT INTO user_images (userId, imageName, imageData) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, currentUserId);
                statement.setString(2, selectedFile.getName());
                statement.setBlob(3, fileInputStream);

                statement.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Image uploaded successfully!");
                loadImagesFromDatabase();
                updateDisplay();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to upload the image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewPlanForUploader() {
        if (imageIds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No image selected to view uploader's plan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int imageId = imageIds.get(currentIndex);

        try {
            String query = "SELECT userId FROM user_images WHERE imageId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, imageId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String uploaderId = resultSet.getString("userId");

                int tripId = getTripIdByUserId(uploaderId);
                if (tripId == -1) {
                    JOptionPane.showMessageDialog(frame, "No trip plan found for this uploader.", "No Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TravelPlan travelPlan = fetchTravelPlan(uploaderId, tripId);
                if (travelPlan == null) {
                    JOptionPane.showMessageDialog(frame, "Failed to load travel plan details.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TravelAppGUI travelAppInstance = new TravelAppGUI(conn);
                travelAppInstance.new App(travelPlan, tripId);

                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Uploader information not found for this image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to retrieve uploader's information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private TravelPlan fetchTravelPlan(String userId, int tripId) {
        try {
            String cityQuery = "SELECT city FROM travelplans WHERE userId = ? AND tripId = ?";
            PreparedStatement cityStmt = conn.prepareStatement(cityQuery);
            cityStmt.setString(1, userId);
            cityStmt.setInt(2, tripId);

            ResultSet cityResultSet = cityStmt.executeQuery();
            ArrayList<String> cityList = new ArrayList<>();

            while (cityResultSet.next()) {
                cityList.add(cityResultSet.getString("city"));
            }

            if (cityList.isEmpty()) {
                return null;
            }

            String[] cities = cityList.toArray(new String[0]);
            return new TravelPlan(cities, userId, tripId, conn);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getTripIdByUserId(String userId) {
        try {
            String query = "SELECT tripId FROM travelplans WHERE userId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("tripId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
