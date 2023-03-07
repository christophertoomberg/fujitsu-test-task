package com.example.demo.repositories;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieBuilder;
import com.example.demo.models.MovieDAO;
import com.example.demo.services.MovieDataBaseService;
import com.example.demo.services.RentalDatabaseService;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {

    private final MovieDataBaseService movieDatabaseService;

    public MovieRepository(MovieDataBaseService movieDatabaseService, RentalDatabaseService rentalDatabaseService) {
        this.movieDatabaseService = movieDatabaseService;
    }

    public List<Movie> getAllMovies() throws IOException {
        return movieDatabaseService.getMovies();
    }

    public Optional<Movie> findMovieById(UUID id) throws IOException {
        return movieDatabaseService
                .getMovies()
                .stream().filter(movie -> movie.getId().equals(id))
                .findFirst();
    }

    public Optional<Movie> findMovieByName(String name) throws IOException {
        return movieDatabaseService
                .getMovies()
                .stream()
                .filter(movie -> movie.getMovieName().equals(name))
                .findFirst();
    }

    public List<Movie> filterMoviesByCategory(Movie.MovieCategory movieCategory) throws IOException {
        return movieDatabaseService
                .getMovies()
                .stream()
                .filter(movie -> movie.getCategory().equals(movieCategory))
                .collect(Collectors.toList());
    }


    public void addMovie(MovieDAO movieDAO) throws IOException {
        Movie movie = new MovieBuilder()
                .setMovieName(movieDAO.getMovieName())
                .setDescription(movieDAO.getDescription())
                .setCategory(movieDAO.getMovieCategory())
                .setActors(movieDAO.getActors())
                .setReleaseDate(movieDAO.getReleaseDate())
                .createMovie();

        movieDatabaseService.addItem(movie);
    }

    public void addMovies(List<MovieDAO> movieDAOs) throws IOException {

        List<Movie> movies = new ArrayList<>();

        for (MovieDAO movieDAO : movieDAOs) {
            movies.add(new MovieBuilder()
                    .setMovieName(movieDAO.getMovieName())
                    .setDescription(movieDAO.getDescription())
                    .setCategory(movieDAO.getMovieCategory())
                    .setActors(movieDAO.getActors())
                    .setReleaseDate(movieDAO.getReleaseDate())
                    .createMovie());
        }

        movieDatabaseService.addItems(movies);
    }

    public void deleteMovie(UUID id) throws IOException {
        movieDatabaseService.deleteItem(id);
    }

    public void updateMovie(UUID id, MovieDAO movieDAO) throws IOException {

        Movie movie = new MovieBuilder()
                .setMovieName(movieDAO.getMovieName())
                .setDescription(movieDAO.getDescription())
                .setCategory(movieDAO.getMovieCategory())
                .setActors(movieDAO.getActors())
                .setReleaseDate(movieDAO.getReleaseDate())
                .createMovie();


        movieDatabaseService.updateItem(id, movie);
    }
}
