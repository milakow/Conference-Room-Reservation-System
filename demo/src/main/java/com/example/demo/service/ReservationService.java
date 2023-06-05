package com.example.demo.service;


import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Reservation;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OrganizationRepository organizationRepository;
    private final ConferenceRoomService conferenceRoomService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              OrganizationRepository organizationRepository,
                              ConferenceRoomService conferenceRoomService) {
        this.reservationRepository = reservationRepository;
        this.organizationRepository = organizationRepository;
        this.conferenceRoomService = conferenceRoomService;
    }

    public List<Reservation> listAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(long id) {
        if (reservationRepository.existsById(id)) {
            return reservationRepository.findById(id).get();
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");
    }

    public void deleteReservation(long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");
    }

    public void addReservation(Reservation reservationToSet, long conferenceRoomId) {
        Reservation reservation = checkReservation(reservationToSet);
        ConferenceRoom conferenceRoom = conferenceRoomService.getConferenceRoomById(conferenceRoomId);
        if (conferenceRoom.isAvailability()){
            if (!reservationRepository.existsById(reservation.getId())) {
                reservation.setConferenceRoom(conferenceRoom);
                reservationRepository.save(reservation);
            } else throw new IllegalArgumentException("Reservation with id " + reservation.getId() + " already exists.");
        }
        else throw new IllegalArgumentException("Conference room with id " + conferenceRoom.getId() + " is not available right now.");
    }

    public Reservation checkReservation(Reservation reservation) {
        if (reservation.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation can't start in the past");
        }
        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            throw new IllegalArgumentException("Start of reservation must be before its end.");
        }
        return reservation;
    }

    public void updateReservation(long id, Reservation updatedReservation) {
        if (reservationRepository.existsById(id)) {
            updatedReservation.setId(id);
            reservationRepository.save(updatedReservation);
        } else throw new IllegalArgumentException("Reservation with id " + id + " does not exist.");

    }
}
