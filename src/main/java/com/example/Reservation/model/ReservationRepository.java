package com.example.Reservation.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationRepository {
    String url = "jdbc:mysql://localhost:3306/reservation?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql = "";

    ArrayList <Reservation> reservations = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    User user;

    public void addReservationToDatabase(Reservation reservation, User user){
        sql = "INSERT INTO `reservation`(`user_id`, `amount_people`, `restaurant_id`) VALUES " +
                "("+ user.getId() +","+reservation.getAmountPeople()+","+reservation.getRestaurantId()+")";
        databaseConnector.insertData(sql);
        this.user = user;
    }
    public void fetchReservations(User user) {
        reservations.clear();
        sql = "SELECT reservation.amount_people, reservation.id, restaurant.name FROM reservation " +
                "INNER JOIN restaurant ON reservation.restaurant_id = restaurant.id " +
                "WHERE user_id = "+ user.getId() +"";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while ((rs.next())) {
                String name = rs.getString("name");
                int amountPeople = rs.getInt("amount_people");
                int id = rs.getInt("id");
                Reservation reservation = new Reservation(amountPeople, name, id);
                reservations.add(reservation);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get menus");
        }
    }
    public Reservation fetchOneReservation(int id) {
        Reservation reservation = null;
        sql = "SELECT reservation.amount_people, reservation.id, restaurant.name FROM reservation " +
                "INNER JOIN restaurant ON reservation.restaurant_id = restaurant.id " +
                "WHERE reservation.id = "+ id +"";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while ((rs.next())) {
                String name = rs.getString("name");
                int amountPeople = rs.getInt("amount_people");
                reservation = new Reservation(amountPeople, name, id);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get menus");
        }
        return reservation;
    }
    public void deleteReservation(int reservationId){
        sql = "DELETE from reservation WHERE id = "+ reservationId + "";
        databaseConnector.updateData(sql);
    }
    public void changeReservation(int amountPeople, int reservationId){
        sql = "UPDATE `reservation` SET `amount_people`="+amountPeople+" WHERE id ="+reservationId+"";
        databaseConnector.updateData(sql);
    }
    public void addNewReservationToList(Reservation reservation){
        reservations.add(reservation);
    }
    public ArrayList<Reservation> getAllReservations(){
        return reservations;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
