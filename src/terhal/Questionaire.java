
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
    //date TravelDuration;
    String TravelDuration;
    float budget;
    User user ;
    List<String> questions;
    Countries countries ;
    List<String> responses;
}
