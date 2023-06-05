package com.example.demo.service;

import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceRoomService {
    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;
    @Autowired
    ReservationService reservationService;

    public List<ConferenceRoom> listAllConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom getConferenceRoomById(long id) {
        if (conferenceRoomRepository.existsById(id)) {
            return conferenceRoomRepository.findById(id).get();
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }

    public void deleteConferenceRoom(long id) {
        if (conferenceRoomRepository.existsById(id)) {
            conferenceRoomRepository.deleteById(id);
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }

    public void addReservationToConferenceRoom(long reservationId, long conferenceRoomId) {
        ConferenceRoom conferenceRoom = getConferenceRoomById(conferenceRoomId);
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (conferenceRoom.isAvailability()) {
            reservation.setConferenceRoom(conferenceRoom);
            conferenceRoomRepository.save(conferenceRoom);
        } else
            throw new IllegalArgumentException("Conference room with id " + conferenceRoomId + " is not available for reservation.");
    }

    public void addConferenceRoom(ConferenceRoom conferenceRoom) {
        if (!conferenceRoomRepository.existsById(conferenceRoom.getId())) {
            conferenceRoomRepository.save(conferenceRoom);
        } else
            throw new IllegalArgumentException("Conference room with id " + conferenceRoom.getId() + " already exists.");
    }

    public void updateConferenceRoom(long id, ConferenceRoom updatedConferenceRoom) {
        if (conferenceRoomRepository.existsById(id)) {
            updatedConferenceRoom.setId(id);
            conferenceRoomRepository.save(updatedConferenceRoom);
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }
}
