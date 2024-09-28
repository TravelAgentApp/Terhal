
package terhal;

import java.util.List;

/**
 *
 * @author janaz
 */
public class Questionaire {
    /*
    Activity  =new Activity();
    Hotel  =new Hotel();
    Flight  =new Flight();
    User  =new User();
    */
    
    String questionId; 
    int members ; 
    int TravelPurpose;
    TravelPlan travelPlan; 
    int FlightId ;
    int HotelId ;
    int ActivityId;
    int RestaurantId;
    String Weather;
    String TravelDuration;
    float budget;
    User user ;
    //List<String> questions;
    Countries countries ;
    //List<String> responses;
    
    public Questionaire () {
        
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getTravelPurpose() {
        return TravelPurpose;
    }

    public void setTravelPurpose(int TravelPurpose) {
        this.TravelPurpose = TravelPurpose;
    }

    public TravelPlan getTravelPlan() {
        return travelPlan;
    }

    public void setTravelPlan(TravelPlan travelPlan) {
        this.travelPlan = travelPlan;
    }

    public int getFlightId() {
        return FlightId;
    }

    public void setFlightId(int FlightId) {
        this.FlightId = FlightId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int HotelId) {
        this.HotelId = HotelId;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int ActivityId) {
        this.ActivityId = ActivityId;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int RestaurantId) {
        this.RestaurantId = RestaurantId;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public String getTravelDuration() {
        return TravelDuration;
    }

    public void setTravelDuration(String TravelDuration) {
        this.TravelDuration = TravelDuration;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Countries getCountries() {
        return countries;
    }

    public void setCountries(Countries countries) {
        this.countries = countries;
    }

  
    
}
