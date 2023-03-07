package com.example.demo.services;

import com.example.demo.models.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieDataBaseService {

    @Value("${useYaml}")
    private boolean useYaml;

    public void config(boolean useYaml) {
        this.useYaml = useYaml;
    }
    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() throws IOException {
        return loadMovies();
    }
    public void addItem(Movie movieToBeAdded) throws IOException {
        List<Movie> loadedMovies = loadMovies();
        loadedMovies.add(movieToBeAdded);
        movies = loadedMovies;

        saveMovies();
        movies.clear();
    }

    public void addItems(List<Movie> moviesToBeAdded) throws IOException {
        List<Movie> loadedMovies = loadMovies();
        loadedMovies.addAll(moviesToBeAdded);
        movies = loadedMovies;

        saveMovies();
        movies.clear();
    }

    public void updateItem(UUID id, Movie newMovie) throws IOException {
        movies = loadMovies();

        if (movies.isEmpty()) return;
        Optional<Movie> foundMovie = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
        if (foundMovie.isPresent()) {
            newMovie.setId(id);
            movies.remove(foundMovie.get());
            movies.add(newMovie);
        } else {
            return;
        }
        saveMovies();
    }

    public void deleteItem(UUID id) throws IOException {
        movies = loadMovies();
        if (movies.isEmpty()) return;
        movies = movies.stream().filter(movie -> !movie.getId().equals(id)).collect(Collectors.toList());

        saveMovies();
    }

    private List<Movie> loadMovies() throws IOException {
        if (useYaml) {
            return FileIOService.readMoviesFromYamlFile();
        } else {
            return FileIOService.readMoviesFromJsonFile();
        }
    }

    private void saveMovies() throws IOException {
        if (useYaml) {
            FileIOService.saveMoviesToYamlFile(movies);
        } else {
            FileIOService.saveMoviesToJsonFile(movies);
        }
    }
}
