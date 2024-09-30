
package terhal;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author janaz
 */
public class Questionaire {
    private int tripId; // Primary key
    private String userId; // Foreign key referencing User
    private String purpose; // Purpose of the trip
    private LocalDate durationStart; // Start date of the trip
    private LocalDate durationEnd; // End date of the trip
    private double budget; // Budget for the trip
    private int destination; // Foreign key referencing Country
    private int numMembers; // Number of members traveling
    private String weatherPreference; // Weather preference for the trip
    private String activityPreference; // Activity preferences
    private String cuisinePreference; // Cuisine preferences
    private String hotelPreference; // Hotel preference
    private String flightPreference; // Flight preference

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(LocalDate durationStart) {
        this.durationStart = durationStart;
    }

    public LocalDate getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(LocalDate durationEnd) {
        this.durationEnd = durationEnd;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public String getWeatherPreference() {
        return weatherPreference;
    }

    public void setWeatherPreference(String weatherPreference) {
        this.weatherPreference = weatherPreference;
    }

    public String getActivityPreference() {
        return activityPreference;
    }

    public void setActivityPreference(String activityPreference) {
        this.activityPreference = activityPreference;
    }

    public String getCuisinePreference() {
        return cuisinePreference;
    }

    public void setCuisinePreference(String cuisinePreference) {
        this.cuisinePreference = cuisinePreference;
    }

    public String getHotelPreference() {
        return hotelPreference;
    }

    public void setHotelPreference(String hotelPreference) {
        this.hotelPreference = hotelPreference;
    }

    public String getFlightPreference() {
        return flightPreference;
    }

    public void setFlightPreference(String flightPreference) {
        this.flightPreference = flightPreference;
    }

    
}