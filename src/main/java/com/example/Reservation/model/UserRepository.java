package com.example.Reservation.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository {
    ReservationRepository reservationRepository = new ReservationRepository();
    String url = "jdbc:mysql://localhost:3306/reservation?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql = "";
    User user;

    ArrayList<User> users = new ArrayList<>();

    public void fetchUser() {
        users.clear();
        databaseConnector.buildConnection();
        sql = "SELECT * FROM user";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while ((rs.next())) {
                String mail = rs.getString("mail");
                int id = rs.getInt("id");
                String password = rs.getString("password");
                user = new User(mail,password,id);
                users.add(user);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get User");
        }

    }
    public User comparePasswordAndMail(String inputMail, String inputPassword){
        fetchUser();
        for(User user: users){
            if(inputMail.equals(user.getMail()) && inputPassword.equals(user.getPassword())){
                this.user = user;
                reservationRepository.setUser(user);
                break;
            }
        }
        return user;
    }
    public String compareUser(User userFromLogin){
        String token="";
        User actualUser = null;
        for (User user : users){
            if (user.getMail().equalsIgnoreCase(userFromLogin.getMail())
                    && user.getPassword().equalsIgnoreCase(userFromLogin.getPassword())){
                actualUser = user;
                token = generateToken(user);
                actualUser.setToken(token);
            }
        }
        return token;
    }
    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        return token;
    }
    public ArrayList<User> getUsers(){
        return users;
    }
}
