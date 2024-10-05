
package terhal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author s4ooo
 */
class Countries {
    private int countryId; // Primary key
    private String countryName; // Name of the country
    private boolean visaRequired; // Whether a visa is required
    private String weather;
    
     private Connection conn;

    public Countries(Connection connection) {
        this.conn = connection; // Assign the connection
    }
    
    // Method to return a list of countries based on budget, weather, and activity preferences
 public List<String> countryNames = new ArrayList<>();  // To store country names
public List<String> cityNames = new ArrayList<>();     // To store city names

 public List<String[]> selectCountry(double budgetPercentage, String weatherPreference, String activityPreference) {
    List<String[]> countries = new ArrayList<>();
    List<Integer> countryIds = new ArrayList<>();

    try {
        // Step 1: Select countries where flight price is within the budget and match the arrival airport in the Countries table
        String flightQuery = "SELECT DISTINCT countryId FROM Countries WHERE airport_code IN (SELECT arrival FROM Flights WHERE price <= ?)";
        try (PreparedStatement flightStmt = conn.prepareStatement(flightQuery)) {
            flightStmt.setDouble(1, budgetPercentage);
            ResultSet flightRs = flightStmt.executeQuery();

            // Collect the country IDs from the query result
            while (flightRs.next()) {
                countryIds.add(flightRs.getInt("countryId"));
            }
        }

        // Step 2: Filter countries by weather preference
        List<Integer> weatherFilteredCountries = new ArrayList<>();
        String weatherQuery = "SELECT countryId FROM Countries WHERE weather = ?";
        try (PreparedStatement weatherStmt = conn.prepareStatement(weatherQuery)) {
            weatherStmt.setString(1, weatherPreference);
            ResultSet weatherRs = weatherStmt.executeQuery();

            // Filter the countryIds based on weather preference
            while (weatherRs.next()) {
                int countryId = weatherRs.getInt("countryId");
                if (countryIds.contains(countryId)) {
                    weatherFilteredCountries.add(countryId);
                }
            }
        }

        // Step 3: Filter countries by activity preference
        String activityQuery = "SELECT DISTINCT location FROM Activities WHERE activity_type = ?";
        try (PreparedStatement activityStmt = conn.prepareStatement(activityQuery)) {
            activityStmt.setString(1, activityPreference);
            ResultSet activityRs = activityStmt.executeQuery();

            // Filter the countryIds based on activity preference
            while (activityRs.next()) {
                int countryId = activityRs.getInt("location");
                if (weatherFilteredCountries.contains(countryId)) {
                    // Step 4: Add country and city names to the final list
                    String countryNameQuery = "SELECT country_name, city FROM Countries WHERE countryId = ?";
                    try (PreparedStatement countryNameStmt = conn.prepareStatement(countryNameQuery)) {
                        countryNameStmt.setInt(1, countryId);
                        ResultSet countryNameRs = countryNameStmt.executeQuery();

                        if (countryNameRs.next()) {
                            String countryName = countryNameRs.getString("country_name");
                            String cityName = countryNameRs.getString("city");
                            countries.add(new String[]{countryName, cityName});
                        }
                    }
                }
            }
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return countries; // Return the list of country-city pairs
}

}