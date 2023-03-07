package com.example.demo.models;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MovieBuilder {
    private String movieName;
    private String description;
    private List<String> actors;
    private Calendar releaseDate;
    private Movie.MovieCategory category;

    public MovieBuilder setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public MovieBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieBuilder setActors(List<String> actors) {
        this.actors = actors;
        return this;
    }

    public MovieBuilder setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public MovieBuilder setCategory(Movie.MovieCategory category) {
        this.category = category;
        return this;
    }

    public Movie createMovie() {
        return new Movie(movieName, description, actors, releaseDate, category);
    }
}