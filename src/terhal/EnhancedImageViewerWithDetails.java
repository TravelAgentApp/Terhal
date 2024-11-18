package terhal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class EnhancedImageViewerWithDetails {
    private JFrame frame;
    private JLabel imageLabel, publisherLabel, locationLabel;
    private JPanel commentPanel;
    private JButton likeButton, dislikeButton, addCommentButton, nextButton, prevButton, uploadButton, viewPlanButton;
    private JLabel likeCountLabel, dislikeCountLabel;
    private ArrayList<String> images = new ArrayList<>();
    private int[] likeCounts;
    private int[] dislikeCounts;
    private HashMap<String, ArrayList<Comment>> comments = new HashMap<>();
    private int currentIndex = 0;

    private final String STORAGE_FOLDER = "image_storage";
    private final String DATA_FILE = STORAGE_FOLDER + "/data.txt";

    public EnhancedImageViewerWithDetails() {
        frame = new JFrame("Explore Saudi Arabia Experiences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(34, 49, 63));

        File storageDir = new File(STORAGE_FOLDER);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        publisherLabel = new JLabel("", SwingConstants.CENTER);
        publisherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        publisherLabel.setForeground(new Color(0, 230, 118));
        publisherLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 150, 136)));

        locationLabel = new JLabel("", SwingConstants.CENTER);
        locationLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        locationLabel.setForeground(new Color(76, 175, 80));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(publisherLabel, BorderLayout.NORTH);
        headerPanel.add(locationLabel, BorderLayout.SOUTH);
        frame.add(headerPanel, BorderLayout.NORTH);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(imageLabel, BorderLayout.CENTER);

        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBackground(new Color(220, 237, 200));
        commentPanel.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));

        likeButton = new JButton("❤️ Like");
        dislikeButton = new JButton("👎 Dislike");
        addCommentButton = new JButton("Add Comment");
        uploadButton = new JButton("Upload Experience");
        viewPlanButton = new JButton("View Plan");
        likeButton.setBackground(new Color(200, 230, 201));
        dislikeButton.setBackground(new Color(200, 230, 201));
        addCommentButton.setBackground(new Color(76, 175, 80));
        addCommentButton.setForeground(Color.WHITE);
        uploadButton.setBackground(new Color(76, 175, 80));
        uploadButton.setForeground(Color.WHITE);
        viewPlanButton.setBackground(new Color(76, 175, 80));
        viewPlanButton.setForeground(Color.WHITE);
        likeCountLabel = new JLabel("Likes: 0");
        dislikeCountLabel = new JLabel("Dislikes: 0");
        likeCountLabel.setForeground(Color.WHITE);
        dislikeCountLabel.setForeground(Color.WHITE);

        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        nextButton.setBackground(new Color(76, 175, 80));
        prevButton.setBackground(new Color(76, 175, 80));
        nextButton.setForeground(Color.WHITE);
        prevButton.setForeground(Color.WHITE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 4, 5, 5));
        bottomPanel.setBackground(new Color(34, 49, 63));
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(addCommentButton);
        bottomPanel.add(uploadButton);
        bottomPanel.add(likeButton);
        bottomPanel.add(dislikeButton);
        bottomPanel.add(viewPlanButton);
        bottomPanel.add(likeCountLabel);
        bottomPanel.add(dislikeCountLabel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(34, 49, 63));
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        JScrollPane commentScrollPane = new JScrollPane(commentPanel);
        commentScrollPane.setPreferredSize(new Dimension(150, 0));
        contentPanel.add(commentScrollPane, BorderLayout.WEST);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        loadStoredData();
        updateDisplay();

        likeButton.addActionListener(e -> toggleLike());
        dislikeButton.addActionListener(e -> toggleDislike());
        addCommentButton.addActionListener(e -> addNewComment());
        uploadButton.addActionListener(e -> uploadNewExperience());
        nextButton.addActionListener(e -> {
            if (!images.isEmpty()) {
                currentIndex = (currentIndex + 1) % images.size();
                updateDisplay();
            }
        });
        prevButton.addActionListener(e -> {
            if (!images.isEmpty()) {
                currentIndex = (currentIndex - 1 + images.size()) % images.size();
                updateDisplay();
            }
        });

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void loadStoredData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            likeCounts = new int[0];
            dislikeCounts = new int[0];
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                images.add(line.trim());
                comments.put(line.trim(), new ArrayList<>());
            }
            likeCounts = new int[images.size()];
            dislikeCounts = new int[images.size()];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStoredData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (String image : images) {
                writer.write(image);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDisplay() {
        if (images.isEmpty() || currentIndex < 0 || currentIndex >= images.size()) {
            JOptionPane.showMessageDialog(frame, "No images to display!");
            return;
        }

        String currentImage = images.get(currentIndex);
        imageLabel.setIcon(new ImageIcon(currentImage));
        likeCountLabel.setText("Likes: " + likeCounts[currentIndex]);
        dislikeCountLabel.setText("Dislikes: " + dislikeCounts[currentIndex]);
        updateCommentPanel(currentImage);
    }

    private void toggleLike() {
        if (!images.isEmpty()) {
            likeCounts[currentIndex]++;
            updateDisplay();
        }
    }

    private void toggleDislike() {
        if (!images.isEmpty()) {
            dislikeCounts[currentIndex]++;
            updateDisplay();
        }
    }

    private void addNewComment() {
        if (!images.isEmpty()) {
            String newComment = JOptionPane.showInputDialog(frame, "Enter your comment:");
            if (newComment != null && !newComment.trim().isEmpty()) {
                String currentImage = images.get(currentIndex);
                comments.computeIfAbsent(currentImage, k -> new ArrayList<>())
                        .add(new Comment("Current User", newComment.trim(), LocalDateTime.now()));
                updateDisplay();
            }
        }
    }

    private void uploadNewExperience() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File destination = new File(STORAGE_FOLDER + "/" + selectedFile.getName());

            try {
                if (!destination.exists()) {
                    Files.copy(selectedFile.toPath(), destination.toPath());
                    images.add(destination.getAbsolutePath());
                    comments.put(destination.getAbsolutePath(), new ArrayList<>());
                    likeCounts = java.util.Arrays.copyOf(likeCounts, images.size());
                    dislikeCounts = java.util.Arrays.copyOf(dislikeCounts, images.size());
                    updateStoredData();
                    updateDisplay();
                } else {
                    JOptionPane.showMessageDialog(frame, "Image already exists!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCommentPanel(String imagePath) {
        commentPanel.removeAll();
        ArrayList<Comment> commentList = comments.get(imagePath);
        if (commentList != null) {
            for (Comment comment : commentList) {
                JLabel commentLabel = new JLabel("[" + comment.timestamp + "] " + comment.username + ": " + comment.text);
                commentPanel.add(commentLabel);
            }
        }
        commentPanel.revalidate();
        commentPanel.repaint();
    }

    static class Comment {
        String username;
        String text;
        LocalDateTime timestamp;

        public Comment(String username, String text, LocalDateTime timestamp) {
            this.username = username;
            this.text = text;
            this.timestamp = timestamp;
        }
    }
}
