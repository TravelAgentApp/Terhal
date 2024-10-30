
package terhal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.sql.Types;

import javax.swing.JOptionPane;

/**
 *
 * @author s4ooo
 */
class TravelPlan {
  int countryID;
  String userID;
  int tripID;
  int hotelID;
  int flightID;
  String[] citynames;
  private Connection conn;


    public TravelPlan(String[] citynames, String userID, int tripID, Connection conn) {
        this.countryID = countryID;
        this.userID = userID;
        this.tripID = tripID;
        this.conn = conn;
        this.citynames = citynames;
    }

    public String[] getCitynames() {
        return citynames;
    }
  
    
    public Map<String, List<Integer>> getActivitiesByTime(String city) {
    Map<String, List<Integer>> activitiesByTime = new HashMap<>();
    activitiesByTime.put("morning", new ArrayList<>());
    activitiesByTime.put("afternoon", new ArrayList<>());
    activitiesByTime.put("evening", new ArrayList<>());

    String query = "SELECT activityId, best_time_to_go FROM Activities WHERE location = (SELECT countryId FROM Countries WHERE city = ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, city);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int activityId = rs.getInt("activityId");
            String bestTime = rs.getString("best_time_to_go");

            // Categorize the activity based on time of day
            if (bestTime.compareTo("12:00:00") <= 0) {
                activitiesByTime.get("morning").add(activityId);
            } else if (bestTime.compareTo("18:00:00") <= 0) {
                activitiesByTime.get("afternoon").add(activityId);
            } else {
                activitiesByTime.get("evening").add(activityId);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activitiesByTime;
}

public List<Integer> getRestaurantsByCity(String city) {
    List<Integer> restaurants = new ArrayList<>();
    String query = "SELECT restaurantId FROM Restaurants WHERE city = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, city);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            restaurants.add(rs.getInt("restaurantId"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return restaurants;
}

  public String getActivityNameById(int activityId) {
    String activityName = "";
    String query = "SELECT name FROM Activities WHERE activityId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, activityId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            activityName = rs.getString("name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activityName;
}

public String getRestaurantNameById(int restaurantId) {
    String restaurantName = "";
    String query = "SELECT name FROM Restaurants WHERE restaurantId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, restaurantId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            restaurantName = rs.getString("name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return restaurantName;
}
 
public String getActivityDetailsById(int activityId) {
    StringBuilder activityDetails = new StringBuilder();
    String query = "SELECT name, purpose, activity_constraints, best_time_to_go, cost, activity_type FROM Activities WHERE activityId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, activityId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            activityDetails.append("Name: ").append(rs.getString("name"))
                
                .append("\nPurpose: ").append(rs.getString("purpose"))
                .append("\nConstraints: ").append(rs.getString("activity_constraints"))
                .append("\nBest Time To Go: ").append(rs.getString("best_time_to_go"))
                .append("\nCost: ").append(rs.getDouble("cost"))
                .append("\nType: ").append(rs.getString("activity_type"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activityDetails.toString();
}

public String getRestaurantDetailsById(int restaurantId) {
    StringBuilder restaurantDetails = new StringBuilder();
    String query = "SELECT name, cuisine, price_range, rating, city FROM Restaurants WHERE restaurantId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, restaurantId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            restaurantDetails.append("Name: ").append(rs.getString("name"))
                .append("\nCuisine: ").append(rs.getString("cuisine"))
                .append("\nPrice Range: ").append(rs.getString("price_range"))
                .append("\nRating: ").append(rs.getDouble("rating"))
                .append("\nCity: ").append(rs.getString("city"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return restaurantDetails.toString();
}
public boolean isActivityId(int id) {
    String query = "SELECT COUNT(*) FROM Activities WHERE activityId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isRestaurantId(int id) {
    String query = "SELECT COUNT(*) FROM Restaurants WHERE restaurantId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

//////
     public String getCuisinePrefference (int tripId, String UserId) {
    String restaurants = "";
    String query = "SELECT cuisine_preference FROM trips WHERE userId = ? AND tripId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, UserId);
        pstmt.setInt(2, tripId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            restaurants= rs.getString("cuisine_preference");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return restaurants;
}
     
    public String getactivityPrefference (int tripId, String UserId) {
    String activity = "";
    String query = "SELECT activity_preference FROM trips WHERE userId = ? AND tripId = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, UserId);
        pstmt.setInt(2, tripId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            activity= rs.getString("activity_preference");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activity;
}

     public List<Integer> getRestaurantsByPreference(String city, String cuisinePreference) {
    List<Integer> restaurants = new ArrayList<>();
    String query = "SELECT restaurantId FROM Restaurants WHERE city = ? AND cuisine = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, city);
        pstmt.setString(2, cuisinePreference);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            restaurants.add(rs.getInt("restaurantId"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return restaurants;
}
     
public int getduration (int tripId, String UserId) {
    int i = 0;
    String query = "SELECT travelDuration FROM trips WHERE userId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, UserId);
 
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            i= rs.getInt("travelDuration");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
          }


public List<Day> createDailyItinerary(Map<String, List<Integer>> activitiesByTime, List<Integer> restaurants, int travelDuration) {
    List<Day> days = new ArrayList<>();
    
    for (int i = 1; i <= travelDuration; i++) {
        Day day = new Day("Day " + i);

        // Assign activities for each time of day
        day.setMorningActivity(getNextActivity(activitiesByTime.get("morning")));
        day.setEveningActivity(getNextActivity(activitiesByTime.get("afternoon")));
        day.setNightActivity(getNextActivity(activitiesByTime.get("evening")));

        // Assign restaurants for each meal (2 meals a day in this case)
        day.setRestaurantBreakfast(getNextRestaurant(restaurants));
        day.setRestaurantDinner(getNextRestaurant(restaurants));

        days.add(day);
    }
    return days;
}

// Helper method to get next activity from list and cycle through
private Integer getNextActivity(List<Integer> activities) {
    if (!activities.isEmpty()) {
        return activities.remove(0);
    }
    return null; // No more activities
}

// Helper method to get next restaurant from list and cycle through
private Integer getNextRestaurant(List<Integer> restaurants) {
    if (!restaurants.isEmpty()) {
        return restaurants.remove(0);
    }
    return null; // No more restaurants
}

//for presenting options
public List<String> getActivityNamesByCityId(int cityId) {
    List<String> activityNames = new ArrayList<>();
    String query = "SELECT name FROM activities WHERE location = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, cityId); // Set the city name parameter
        ResultSet rs = pstmt.executeQuery();

        // Loop through the result set and add activity names to the list
        while (rs.next()) {
            activityNames.add(rs.getString("name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving activities for city: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return activityNames;
}

//for presenting travel options
public List<String> getRestaurantNamesByCity(String cityName) {
    List<String> restaurantNames = new ArrayList<>();
    String query = "SELECT name FROM restaurants WHERE city = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, cityName);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            restaurantNames.add(rs.getString("name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving restaurants for city: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return restaurantNames;
}

public int getActivityIdByName(String activityName) {
    String query = "SELECT activityId FROM activities WHERE name = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, activityName);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("activityId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return -1 if not found
}

public int getRestaurantIdByName(String restaurantName) {
    String query = "SELECT restaurantId FROM restaurants WHERE name = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, restaurantName);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("restaurantId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return -1 if not found
}

public int getCitytIdByName(String Cityname) {
    String query = "SELECT countryId FROM Countries WHERE City = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, Cityname);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("countryId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return -1 if not found
}
public void saveDaysToDatabase(int travelPlanId, List<Day> days) {
    String query = "INSERT INTO day (travelPlanId, day_name, morningActivity, eveningActivity, nightActivity, restaurantBreakfast, restaurantLunch, restaurantDinner) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        for (Day day : days) {
            pstmt.setInt(1, travelPlanId);
            pstmt.setString(2, day.getDayName());
            pstmt.setObject(3, day.getMorningActivity(), Types.INTEGER);
            pstmt.setObject(4, day.getEveningActivity(), Types.INTEGER);
            pstmt.setObject(5, day.getNightActivity(), Types.INTEGER);
            pstmt.setObject(6, day.getRestaurantBreakfast(), Types.INTEGER);
            pstmt.setObject(7, day.getRestaurantLunch(), Types.INTEGER);
            pstmt.setObject(8, day.getRestaurantDinner(), Types.INTEGER);
            pstmt.addBatch(); // Batch for better performance
        }
        pstmt.executeBatch();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}

