
package terhal;

/**
 *
 * @author s4ooo
 */
 class Activity {
    private int activityId; // Primary key
    private String name; // Name of the activity
    private String location; // Location ID of the activity
    private String purpose; // Purpose of the activity
    private String activityConstraints; // Any constraints related to the activity
    private String bestTimeToGo; // Best time to engage in the activity
    private double cost; // Cost of the activity

    // Constructors, getters, and setters

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getActivityConstraints() {
        return activityConstraints;
    }

    public void setActivityConstraints(String activityConstraints) {
        this.activityConstraints = activityConstraints;
    }

    public String getBestTimeToGo() {
        return bestTimeToGo;
    }

    public void setBestTimeToGo(String bestTimeToGo) {
        this.bestTimeToGo = bestTimeToGo;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public void getactivities(){
        
    }
}

