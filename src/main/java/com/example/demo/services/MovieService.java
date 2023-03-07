package com.example.demo.services;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieDAO;
import com.example.demo.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() throws IOException {
        return movieRepository.getAllMovies();
    }

    public Optional<Movie> findMovieById(UUID id) throws IOException {
        return movieRepository.findMovieById(id);
    }

    public Optional<Movie> findMovieByName(String name) throws IOException {
        return movieRepository.findMovieByName(name);
    }

    public List<Movie> filterMoviesByCategory(Movie.MovieCategory movieCategory) throws IOException {
        return movieRepository.filterMoviesByCategory(movieCategory);
    }

    public void addMovie(MovieDAO movieDAO) throws IOException {
        movieRepository.addMovie(movieDAO);
    }

    public void addMovies(List<MovieDAO> movieDAOS) throws IOException {
        movieRepository.addMovies(movieDAOS);
    }

    public void deleteMovie(UUID id) throws IOException {
        movieRepository.deleteMovie(id);
    }

    public void updateMovie(UUID id, MovieDAO movieDAO) throws IOException {
        movieRepository.updateMovie(id, movieDAO);
    }
}

