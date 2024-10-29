
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
  
  
    
 /*
  public Map<String, List<Integer>> getActivitiesByTime(String city, String activityPreference) {
    Map<String, List<Integer>> activitiesByTime = new HashMap<>();
    activitiesByTime.put("morning", new ArrayList<>());
    activitiesByTime.put("evening", new ArrayList<>());
    activitiesByTime.put("night", new ArrayList<>());

    String query = "SELECT activityId, best_time_to_go FROM Activities "
                 + "WHERE location = (SELECT countryId FROM Countries WHERE city = ?) "
                 + "AND activity_type = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, city);
        pstmt.setString(2, activityPreference);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int activityId = rs.getInt("activityId");
            String bestTime = rs.getString("best_time_to_go");

            // Sort activities into morning, evening, or night based on best_time_to_go
            if (bestTime.startsWith("09") || bestTime.startsWith("10") || bestTime.startsWith("11")) {
                activitiesByTime.get("morning").add(activityId);
            } else if (bestTime.startsWith("12") || bestTime.startsWith("13") || bestTime.startsWith("14") || bestTime.startsWith("15")) {
                activitiesByTime.get("evening").add(activityId);
            } else if (bestTime.startsWith("16") || bestTime.startsWith("17") || bestTime.startsWith("18") || bestTime.startsWith("19")) {
                activitiesByTime.get("night").add(activityId);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activitiesByTime;
}

*/
    
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
/*
     public List<Day> createDailyItinerary(Map<String, List<Integer>> activitiesByTime, List<Integer> restaurants, int travelDuration) {
    List<Day> days = new ArrayList<>();

    for (int i = 1; i <= travelDuration; i++) {
        Day day = new Day("Day " + i);

        // Assign activities for each time of day
        day.setMorningActivity(getNextActivity(activitiesByTime.get("morning")));
        day.setEveningActivity(getNextActivity(activitiesByTime.get("evening")));
        day.setNightActivity(getNextActivity(activitiesByTime.get("night")));

        // Assign restaurants for each meal
        day.setRestaurantBreakfast(getNextRestaurant(restaurants));
        day.setRestaurantLunch(getNextRestaurant(restaurants));
        day.setRestaurantDinner(getNextRestaurant(restaurants));

        days.add(day);
    }
    return days;
}
*/

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

