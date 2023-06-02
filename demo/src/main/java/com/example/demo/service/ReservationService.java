package com.example.demo.service;


import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> listAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(long id){
        if (reservationRepository.existsById(id)) {
            return reservationRepository.findById(id).get();
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");    }

    public void deleteReservation(long id){
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");    }

    public void addReservation(Reservation reservation){
        if(!reservationRepository.existsById(reservation.getId())){
            reservationRepository.save(reservation);
        } else throw new IllegalArgumentException("Reservation with id " + reservation.getId() + " already exists.");

    }

    public void updateReservation(long id, Reservation updatedReservation) {
        if(reservationRepository.existsById(id)){
            updatedReservation.setId(id);
            reservationRepository.save(updatedReservation);
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");

    }
}
