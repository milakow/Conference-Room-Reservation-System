package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    @Size(min = 2, max = 20, message = "Reservation identifier length should be between 2 and 20 characters.")
    @NotBlank(message = "Reservation identifier cannot be blank.")
    @Column(unique = true)
    private String identifier;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id", columnDefinition = "bigint")
    private Organization organization;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "conferenceRoom_id", columnDefinition = "bigint")
    private ConferenceRoom conferenceRoom;

    public Reservation(){
    }

    public Reservation(String identifier, LocalDateTime startDate, LocalDateTime endDate) {
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation(long id, String identifier, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

//    public void compareDates(){
//
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
