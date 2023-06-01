package com.example.demo.model;

public enum NumberOfPlaces {
    SITTING(),
    STANDING();
    private int numberOfPlaces;

    NumberOfPlaces(){}
    NumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }
}
