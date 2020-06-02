package com.example.Reservation.api;

import com.example.Reservation.model.Restaurant;
import com.example.Reservation.model.RestaurantRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class RestaurantApiController {
    RestaurantRepository restaurantRepository = new RestaurantRepository();
    ArrayList<Restaurant> allRestaurants = new ArrayList<>();

    public void getRestaurants() {
        restaurantRepository.fetchRestaurants();
        allRestaurants = restaurantRepository.getRestaurants();
    }

    @GetMapping("/restaurants")
    public ArrayList<Restaurant> all() {
        getRestaurants();
        return allRestaurants;
    }
    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurantById(@PathVariable int id){
        Restaurant searchedRestaurant = null;
        getRestaurants();
        for(Restaurant restaurant:allRestaurants){
            if(restaurant.getId() == id){
                searchedRestaurant = restaurant;
                break;
            }
        }
        return searchedRestaurant;
    }
}
