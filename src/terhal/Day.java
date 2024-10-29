/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package terhal;

/**
 *
 * @author janaz
 */
public class Day {
    private String dayName;
    private Integer morningActivity;
    private Integer eveningActivity;
    private Integer nightActivity;
    private Integer restaurantBreakfast;
    private Integer restaurantLunch;
    private Integer restaurantDinner;

    public Day(String dayName) {
        this.dayName = dayName;
    }
    // Getters and setters for all fields...

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Integer getMorningActivity() {
        return morningActivity;
    }

    public void setMorningActivity(Integer morningActivity) {
        this.morningActivity = morningActivity;
    }

    public Integer getEveningActivity() {
        return eveningActivity;
    }

    public void setEveningActivity(Integer eveningActivity) {
        this.eveningActivity = eveningActivity;
    }

    public Integer getNightActivity() {
        return nightActivity;
    }

    public void setNightActivity(Integer nightActivity) {
        this.nightActivity = nightActivity;
    }

    public Integer getRestaurantBreakfast() {
        return restaurantBreakfast;
    }

    public void setRestaurantBreakfast(Integer restaurantBreakfast) {
        this.restaurantBreakfast = restaurantBreakfast;
    }

    public Integer getRestaurantLunch() {
        return restaurantLunch;
    }

    public void setRestaurantLunch(Integer restaurantLunch) {
        this.restaurantLunch = restaurantLunch;
    }

    public Integer getRestaurantDinner() {
        return restaurantDinner;
    }

    public void setRestaurantDinner(Integer restaurantDinner) {
        this.restaurantDinner = restaurantDinner;
    }
    
}
