package com.example.demo.models;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Movie {

    private static final double NEW_MOVIE_PRICE = 5.0;
    private static final double REGULAR_MOVIE_PRICE = 3.49;
    private static final double OLD_MOVIE_PRICE = 1.99;

    private static final int NEW_MOVIE_WEEKS = 52;
    private static final int REGULAR_MOVIE_WEEKS = 156;
    private UUID id;
    private String movieName;
    private String description;
    private List<String> actors;
    private Calendar releaseDate;
    private MovieCategory category;
    private double priceClass;

    public enum MovieCategory {
        ROMANCE,
        COMEDY,
        DRAMA,
        ANIMATION
    }

    // For the ObjectMapper to be able to map entries from JSON to Movie.class.
    public Movie(){}
    public Movie(String movieName, String description, List<String> actors, Calendar releaseDate, MovieCategory category) {

        this.id = UUID.randomUUID();
        this.movieName = movieName;
        this.description = description;
        this.actors = actors;
        this.releaseDate = releaseDate;
        this.category = category;
        this.priceClass = assignMoviePriceClass();
    }

    private double assignMoviePriceClass() {
        long movieAgeInWeeks = (System.currentTimeMillis() - getReleaseDate().getTimeInMillis()) / (1000 * 60 * 60 * 24 * 7);
        double moviePrice;

        if (movieAgeInWeeks <= NEW_MOVIE_WEEKS) {
            moviePrice = NEW_MOVIE_PRICE;
        } else if (movieAgeInWeeks <= REGULAR_MOVIE_WEEKS) {
            moviePrice = REGULAR_MOVIE_PRICE;
        } else {
            moviePrice = OLD_MOVIE_PRICE;
        }
        return moviePrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }


    public String getDescription() {
        return description;
    }


    public List<String> getActors() {
        return actors;
    }


    public Calendar getReleaseDate() {
        return releaseDate;
    }


    public MovieCategory getCategory() {
        return category;
    }

    public void setCategory(MovieCategory movieCategory) {
        category = movieCategory;
    }

    public double getPriceClass() {
        return priceClass;
    }


    public double calculateRentalPrice(int rentalDurationInWeeks) {
        return calculateRentalPrice(rentalDurationInWeeks, System.currentTimeMillis());
    }

    public double calculateRentalPrice(int rentalDurationInWeeks, long currentTime) {

        long releaseTime = releaseDate.getTimeInMillis();
        int movieAgeInWeeks = (int) ((currentTime - releaseTime) / (1000 * 60 * 60 * 24 * 7));

        if (movieAgeInWeeks + rentalDurationInWeeks <= NEW_MOVIE_WEEKS) {
            return rentalDurationInWeeks * NEW_MOVIE_PRICE;
        } else if (movieAgeInWeeks <= NEW_MOVIE_WEEKS) {
            int newMovieWeeks = NEW_MOVIE_WEEKS - movieAgeInWeeks;
            int regularMovieWeeks = rentalDurationInWeeks - newMovieWeeks;
            return (newMovieWeeks * NEW_MOVIE_PRICE) + (regularMovieWeeks * REGULAR_MOVIE_PRICE);
        } else if (movieAgeInWeeks + rentalDurationInWeeks <= REGULAR_MOVIE_WEEKS) {
            return rentalDurationInWeeks * REGULAR_MOVIE_PRICE;
        } else if (movieAgeInWeeks <= REGULAR_MOVIE_WEEKS) {
            int regularMovieWeeks = REGULAR_MOVIE_WEEKS - movieAgeInWeeks;
            int oldMovieWeeks = rentalDurationInWeeks - regularMovieWeeks;
            return (regularMovieWeeks * REGULAR_MOVIE_PRICE) + (oldMovieWeeks * OLD_MOVIE_PRICE);
        } else {
            return rentalDurationInWeeks * OLD_MOVIE_PRICE;
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", description='" + description + '\'' +
                ", actors=" + actors +
                ", releaseDate=" + releaseDate +
                ", Category='" + category + '\'' +
                ", price=" + priceClass +
                '}';
    }
}
