package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "conferenceRoom")
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long id;

    @Size(min = 2, max = 20, message = "Conference room name length should be between 2 and 20 characters.")
    @NotBlank(message = "Conference room name cannot be blank.")
    @Column(unique = true)
    private String name;

    @Size(min = 2, max = 20, message = "Conference room identifier length should be between 2 and 20 characters.")
    @NotBlank(message = "Conference room identifier cannot be blank.")
    @Column(unique = true)
    private String identifier;

    @Min(value = 0, message = "Level value must be at least 0.")
    @Max(value = 10, message = "Level value must be max 10.")
    private int level;

    private boolean availability;

    @Embedded
    private NumberOfPlaces numberOfPlaces;

    @OneToMany(mappedBy = "conferenceRoom")
    List<Reservation> reservationList;

    public ConferenceRoom() {
    }

    public ConferenceRoom(String name, String identifier, int level, boolean availability, NumberOfPlaces numberOfPlaces) {
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfPlaces = numberOfPlaces;
    }

    public ConferenceRoom(long id, String name, String identifier, int level, boolean availability, NumberOfPlaces numberOfPlaces) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfPlaces = numberOfPlaces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public NumberOfPlaces getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(NumberOfPlaces numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
