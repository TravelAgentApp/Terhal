
package terhal;

/**
 *
 * @author sahar
 */
//
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class TravelPlan {
    private User user;
    private Questionaire questions;

    public TravelPlan(User user, Questionaire questions) {
        this.user = user;
        this.questions = questions;
    }

    public void showTravelPlan() {
        JFrame travelPlanFrame = new JFrame("Travel Plan");
        travelPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        travelPlanFrame.setSize(500, 400);
        travelPlanFrame.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Your Travel Plan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea planTextArea = new JTextArea();
        planTextArea.setEditable(false);
        planTextArea.setLineWrap(true);
        planTextArea.setWrapStyleWord(true);
        planTextArea.setText("Here goes your travel plan details...");
        
        StringBuilder travelPlan = new StringBuilder();
        travelPlan.append("Travel Plan for ").append(user.getUsername()).append(":\n");
        travelPlan.append("Budget: ").append(user.getBudget()).append("\n");
        travelPlan.append("Number of Travelers: ").append(user.getNumberOfTravelers()).append("\n");
        travelPlan.append("Travel Duration: ").append(user.getTravelDuration()).append("\n");
        travelPlan.append("Travel Purpose: ").append(user.getTravelPurpose()).append("\n");
        travelPlan.append("Preferences:\n");
        for (String preference : questions.getPreferences()) {
            travelPlan.append("- ").append(preference).append("\n");
        }

        // Recommended travel plan details
        travelPlan.append("\nRecommended Travel Plan:\n");
        travelPlan.append("Destination: Italy\n");
        travelPlan.append("Activities: Sightseeing, Cultural Tours\n");
        travelPlan.append("Restaurants: Trattoria Da Enzo\n");

        JButton backButton = new JButton("Back to Main Info");
        backButton.addActionListener(e -> {
            travelPlanFrame.dispose(); // Close the travel plan window
            questions.showMainInfo(); // Go back to the main info screen
        });

        travelPlanFrame.add(titleLabel);
        travelPlanFrame.add(planTextArea);
        travelPlanFrame.add(backButton);

        travelPlanFrame.setVisible(true);
    }
}
//
//import javax.swing.*;
//
//public class TravelPlan extends JFrame {
//    private User user;
//    private Questionaire questions;
//
//    public TravelPlan(User user, Questionaire questions) {
//        this.user = user;
//        this.questions = questions;
//    }
//        public void showTravelPlan(){
//        JFrame frame = new JFrame("Your Travel Plan");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        
//        StringBuilder travelPlan = new StringBuilder();
//        travelPlan.append("Travel Plan for ").append(user.getUsername()).append(":\n");
//        travelPlan.append("Budget: ").append(user.getBudget()).append("\n");
//        travelPlan.append("Number of Travelers: ").append(user.getNumberOfTravelers()).append("\n");
//        travelPlan.append("Travel Duration: ").append(user.getTravelDuration()).append("\n");
//        travelPlan.append("Travel Purpose: ").append(user.getTravelPurpose()).append("\n");
//        travelPlan.append("Preferences:\n");
//        for (String preference : questions.getPreferences()) {
//            travelPlan.append("- ").append(preference).append("\n");
//        }
//
//        // Recommended travel plan details
//        travelPlan.append("\nRecommended Travel Plan:\n");
//        travelPlan.append("Destination: Italy\n");
//        travelPlan.append("Activities: Sightseeing, Cultural Tours\n");
//        travelPlan.append("Restaurants: Trattoria Da Enzo\n");
//        
//        frame.setVisible(true);
//    }
//}