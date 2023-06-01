package com.example.demo.service;

import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Organization;
import com.example.demo.repository.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceRoomService {
    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    public List<ConferenceRoom> listAllConferenceRooms(){
        return conferenceRoomRepository.findAll();
    };

    public Optional<ConferenceRoom> getConferenceRoomById(long id){
        if (conferenceRoomRepository.existsById(id)) {
            return conferenceRoomRepository.findById(id);
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }

    public void deleteConferenceRoom(long id) {
        if (conferenceRoomRepository.existsById(id)) {
            conferenceRoomRepository.deleteById(id);
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }

    public void addConferenceRoom(ConferenceRoom conferenceRoom){
        if(!conferenceRoomRepository.existsById(conferenceRoom.getId())){
            conferenceRoomRepository.save(conferenceRoom);
        } else throw new IllegalArgumentException("Conference room with id " + conferenceRoom.getId() + " already exists.");
    }

    public void updateConferenceRoom(long id, ConferenceRoom updatedConferenceRoom){
        if(conferenceRoomRepository.existsById(id)){
            updatedConferenceRoom.setId(id);
            conferenceRoomRepository.save(updatedConferenceRoom);
        } else throw new IllegalArgumentException("Conference room with id " + id + " does not exist.");
    }
}
