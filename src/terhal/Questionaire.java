
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
    //int members ;
    int TravelPurpose;
    TravelPlan travelPlan; 
    FlightId flightId ;
    HotelId hotelId ;
    ActivityId activityId ;
    RestaurantId restaurantId;
    String Weather;
    //date TravelDuration;
    String TravelDuration;
    float budget ;
    User user ;
    List<String> questions;
    Countries countries ;
    List<String> responses;
}
