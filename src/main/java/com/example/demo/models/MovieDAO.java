package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.List;

public class MovieDAO {
    @JsonProperty("movieName")
    private String movieName;
    @JsonProperty("description")

    private String description;
    @JsonProperty("actors")

    private List<String> actors;
    @JsonProperty("releaseDate")
    private Calendar releaseDate;
    @JsonProperty("movieCategory")

    private Movie.MovieCategory movieCategory;

    public MovieDAO() {}

    public MovieDAO(String movieName, String description, List<String> actors, Calendar releaseDate,
                    Movie.MovieCategory movieCategory) {
        this.movieName = movieName;
        this.description = description;
        this.actors = actors;
        this.releaseDate = releaseDate;
        this.movieCategory = movieCategory;
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

    public Movie.MovieCategory getMovieCategory() {
        return movieCategory;
    }
}
