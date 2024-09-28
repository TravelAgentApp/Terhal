
package terhal;

import java.util.UUID;

/**
 *
 * @author s4ooo
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private String preferences;
    private String travelPlan;
    private float budget;
    private Questionaire UserQuestionaire;
    //private List<TravelPlan> travelHistory;

    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getTravelPlan() {
        return travelPlan;
    }

    public void setTravelPlan(String travelPlan) {
        this.travelPlan = travelPlan;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Questionaire getUserQuestionaire() {
        return UserQuestionaire;
    }

    public void setUserQuestionaire(Questionaire UserQuestionaire) {
        this.UserQuestionaire = UserQuestionaire;
    }
    
    
}
