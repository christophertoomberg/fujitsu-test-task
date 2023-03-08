package com.example.demo.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

public class MovieDAO {

    @NotBlank(message = "Movie name must not be empty")
    private String movieName;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @NotEmpty(message = "Actors list must not be empty")
    private List<String> actors;
    @NotNull(message = "Release date must not be empty")
    private Calendar releaseDate;
    @NotNull(message = "Movie category must not be empty")
    private Movie.MovieCategory movieCategory;

    public MovieDAO() {}

    public MovieDAO(
            String movieName,
            String description,
            List<String> actors,
            Calendar releaseDate,
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
