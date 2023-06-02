package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class NumberOfPlaces {
    private int sitting;
    private int standing;


    public NumberOfPlaces() {
    }

    public NumberOfPlaces(int sitting, int standing) {
        this.sitting = sitting;
        this.standing = standing;
    }

    public int getSitting() {
        return sitting;
    }

    public void setSitting(int sitting) {
        this.sitting = sitting;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }
}
