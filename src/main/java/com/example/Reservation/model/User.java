package com.example.Reservation.model;

import java.util.ArrayList;

public class User {

    private String name;
    private String mail;
    private String password;
    private int id;
    private String token;
    private ArrayList<Reservation> userReservations = new ArrayList<>();

    public User(String mail, String password, int id){
        this.mail = mail;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addUserReservation(Reservation reservation) {
        userReservations.add(reservation);
    }
}
