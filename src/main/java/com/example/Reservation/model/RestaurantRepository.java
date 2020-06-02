package com.example.Reservation.model;

import com.example.Reservation.model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantRepository {
    String url = "jdbc:mysql://localhost:3306/reservation?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql = "";

    ArrayList<Restaurant> restaurants = new ArrayList<>();
    Restaurant restaurant;

    public void fetchRestaurants() {
        restaurants.clear();
        databaseConnector.buildConnection();
        sql = "SELECT * FROM restaurant";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while ((rs.next())) {
                String location = rs.getString("location");
                int id = rs.getInt("id");
                String name = rs.getString("name");
                restaurant = new Restaurant(location,name,id);
                restaurants.add(restaurant);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get menus");
        }

    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }
}
