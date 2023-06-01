package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    @Size(min = 2, max = 20)
    @NotBlank
    @Column(unique = true)
    private String identifier;

    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(){
    }

    public Reservation(String identifier, LocalDate startDate, LocalDate endDate) {
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation(long id, String identifier, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
