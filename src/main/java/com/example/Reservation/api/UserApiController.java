package com.example.Reservation.api;

import com.example.Reservation.model.Restaurant;
import com.example.Reservation.model.User;
import com.example.Reservation.model.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class UserApiController {
    UserRepository userRepository = new UserRepository();
    ArrayList<User> allUsers = new ArrayList<>();

    public void getAllUser(){
        userRepository.fetchUser();
        allUsers = userRepository.getUsers();
    }
    @GetMapping("/users")
    public ArrayList<User> all() {
        getAllUser();
        return allUsers;
    }
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        User searchedUser = null;
        getAllUser();
        for(User user: allUsers){
            if(user.getId() == id){
                searchedUser = user;
                break;
            }
        }
        return searchedUser;
    }
}
