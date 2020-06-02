package com.example.Reservation.api;

import com.example.Reservation.model.Reservation;
import com.example.Reservation.model.ReservationRepository;
import com.example.Reservation.model.User;
import com.example.Reservation.model.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.apache.naming.ResourceRef.AUTH;

@CrossOrigin
@RestController
public class ReservationApiController {
    ReservationRepository reservationRepository = new ReservationRepository();
    UserRepository userRepository = new UserRepository();
    ArrayList<Reservation> reservations = new ArrayList<>();
    User user;

    public void getReservations() {
        reservationRepository.fetchReservations(user);
        reservations = reservationRepository.getAllReservations();
    }

    @PostMapping("/reservationsPost")
    public void reserve(@RequestBody Reservation newReservation) {
        User user = userRepository.comparePasswordAndMail(newReservation.getMail(), newReservation.getPassword());
        if (user != null) {
            reservationRepository.addReservationToDatabase(newReservation, user);
        }
    }

    @GetMapping("/reservationsGet")
    public ArrayList<Reservation> findUserData() {
        getReservations();
        return reservations;
    }
    @GetMapping("/reservations/{id}")
    public Reservation findOneUserData(@PathVariable int id) {
        Reservation reservation = reservationRepository.fetchOneReservation(id);
        return reservation;
    }

    @PostMapping("/cancellation")
    public void cancel(@RequestBody Reservation reservation) {
        User user = userRepository.comparePasswordAndMail(reservation.getMail(), reservation.getPassword());
        if (user != null) {
            reservationRepository.deleteReservation(reservation.getId());
        }
    }
    @PostMapping("/change")
    public void change(@RequestBody Reservation reservation) {
        reservationRepository.changeReservation(reservation.getAmountPeople(), reservation.getId());
    }
    @PostMapping("/login")
    public void login(@RequestBody Reservation reservation) {
        User user = userRepository.comparePasswordAndMail(reservation.getMail(), reservation.getPassword());
        this.user = user;

    }
}
