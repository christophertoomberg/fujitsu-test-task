package com.example.demo.models;

import java.util.UUID;

public class Rental {

    private UUID id;
    private Movie movie;
    private int rentalDurationInWeeks;
    private double rentalPrice;

    //For ObjectMapper
    public Rental() {}

    public Rental(Movie movie, int rentalDurationInWeeks, double rentalPrice) {

        this.id = UUID.randomUUID();
        this.movie = movie;
        this.rentalDurationInWeeks = rentalDurationInWeeks;
        this.rentalPrice = rentalPrice;
    }

    public UUID getId() {
        return id;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getRentalDurationInWeeks() {
        return rentalDurationInWeeks;
    }


    public double getRentalPrice() {
        return rentalPrice;
    }


    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", movie=" + movie +
                ", rentalDurationInWeeks=" + rentalDurationInWeeks +
                ", rentalPrice=" + rentalPrice +
                '}';
    }
}
