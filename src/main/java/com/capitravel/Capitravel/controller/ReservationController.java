package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.ReservationDTO;
import com.capitravel.Capitravel.dto.ReservationDatesDTO;
import com.capitravel.Capitravel.model.Reservation;
import com.capitravel.Capitravel.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/checkout-passed")
    public ResponseEntity<Boolean> hasCheckoutPassed(@PathVariable Long id) {
        boolean checkoutPassed = reservationService.hasCheckoutPassed(id);
        return new ResponseEntity<>(checkoutPassed, HttpStatus.OK);
    }

    @GetMapping("/experience/{experienceId}")
    public ResponseEntity<List<ReservationDatesDTO>> getReservationsByExperience(@PathVariable Long experienceId) {
        List<ReservationDatesDTO> reservations = reservationService.getReservationsByExperience(experienceId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable String email) {
        List<Reservation> reservations = reservationService.getReservationsByUser(email);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
