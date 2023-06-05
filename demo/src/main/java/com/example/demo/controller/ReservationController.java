package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/all")
    public List<Reservation> listAllReservations() {
        return reservationService.listAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable long id) {
        return reservationService.getReservationById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new/{conferenceRoomId}")
    public void addReservation(@RequestBody Reservation reservation, @PathVariable long conferenceRoomId) {
        reservationService.addReservation(reservation, conferenceRoomId);
    }

    @PutMapping("/update/{id}")
    public void updateReservation(@PathVariable long id, @RequestBody Reservation updatedReservation) {
        reservationService.updateReservation(id, updatedReservation);
    }

}
