package com.example.demo.controller;


import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Organization;
import com.example.demo.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("conferenceRoom")
public class ConferenceRoomController {
    @Autowired
    ConferenceRoomService conferenceRoomService;

    @GetMapping("/all")
    public List<ConferenceRoom> listAllConferenceRooms(){
        return conferenceRoomService.listAllConferenceRooms();
    }

    @GetMapping("/{id}")
    public Optional<ConferenceRoom> getConferenceRoomById(@PathVariable long id){
        return conferenceRoomService.getConferenceRoomById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteConferenceRoom(@PathVariable long id) {
        conferenceRoomService.deleteConferenceRoom(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void addConferenceRoom(@RequestBody ConferenceRoom conferenceRoom){
        conferenceRoomService.addConferenceRoom(conferenceRoom);
    }

    @PutMapping("/update/{id}")
    public void updateConferenceRoom(@PathVariable long id, @RequestBody ConferenceRoom updatedRoom){
        conferenceRoomService.updateConferenceRoom(id, updatedRoom);
    }
}
