/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
package terhal;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author janaz

public class Resturant {
    // Retrieves restaurants for a specific city
public List<String> getRestaurants(String city) {
    List<String> restaurants = new ArrayList<>();

    String query = "SELECT name FROM Restaurants " +
                   "JOIN Countries ON Restaurants.location = Countries.countryId " +
                   "WHERE Countries.city = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, city);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            restaurants.add(rs.getString("name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return restaurants;
}
}
 */