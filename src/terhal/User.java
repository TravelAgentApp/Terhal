
package terhal;

public class User {
    private String username;
    private String email;
    private String password; 
    private double budget;
    private int numberOfTravelers;
    private int travelDuration;
    private String travelPurpose;

    public User(String username, String email, String password, double budget, int numberOfTravelers, int travelDuration, String travelPurpose) {
        this.username = username;
        this.email = email;
        this.password = password; 
        this.budget = budget;
        this.numberOfTravelers = numberOfTravelers;
        this.travelDuration = travelDuration;
        this.travelPurpose = travelPurpose;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password; 
    }

    public double getBudget() {
        return budget;
    }

    public int getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public int getTravelDuration() {
        return travelDuration;
    }

    public String getTravelPurpose() {
        return travelPurpose;
    }
}
