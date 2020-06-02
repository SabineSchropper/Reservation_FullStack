package com.example.Reservation.model;

public class Restaurant {
    private int id;
    private String location;
    private String name;

    public Restaurant(String location, String name, int id) {
        this.location = location;
        this.name = name;
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
