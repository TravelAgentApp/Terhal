
package terhal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author s4ooo
 */
class TravelPlan {
  int countryID;
  int userID;
  int tripID;
  int hotelID;
  int flightID;
  private Connection conn;


    public TravelPlan(int countryID, int userID, int tripID, Connection conn) {
        this.countryID = countryID;
        this.userID = userID;
        this.tripID = tripID;
        this.conn = conn;
    }
  
  
    private String[] getcityNames(String userId) {
    String query = "SELECT city FROM travelplans WHERE userId = ?";

    List<String> cityNamesList = new ArrayList<>(); // Create a list to store city names
    
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userId); // Set the userId parameter
        ResultSet rs = pstmt.executeQuery();

        // Retrieve city names from the result set
        while (rs.next()) {
            cityNamesList.add(rs.getString("city")); // Add each city to the list
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving city names: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Convert the list to an array and return
    return cityNamesList.toArray(new String[0]); // Returns an array containing all cities
}
}

