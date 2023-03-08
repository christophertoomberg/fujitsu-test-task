package com.example.demo.controllers;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieDAO;
import com.example.demo.models.Rental;
import com.example.demo.services.MovieService;
import com.example.demo.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private final MovieService movieService;
    private final RentalService rentalService;

    public MoviesController(MovieService movieService, RentalService rentalService) {
        this.movieService = movieService;
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() throws IOException {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") UUID id) throws IOException {
        Optional<Movie> possibleMovie = movieService.findMovieById(id);
        return possibleMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Movie> searchForMovieByName(@RequestParam String name) throws IOException {
        Optional<Movie> possibleMovie = movieService.findMovieByName(name);
        return possibleMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Movie>> filterMoviesByCategory(@RequestParam Movie.MovieCategory movieCategory) throws IOException {
        return ResponseEntity.ok(movieService.filterMoviesByCategory(movieCategory));
    }

    @PostMapping
    public void addMovie(@Valid @RequestBody MovieDAO movie) throws IOException {
        movieService.addMovie(movie);
    }

    @PostMapping("/add-bulk")
    public void addMovies(@Valid @RequestBody List<MovieDAO> movieDAOS) throws IOException {
        movieService.addMovies(movieDAOS);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable("id") UUID id) throws IOException {
        movieService.deleteMovie(id);
    }

    @PutMapping("/{id}")
    public void updateMovieById(@PathVariable("id") UUID id, @Valid @RequestBody MovieDAO movie) throws IOException {
        movieService.updateMovie(id, movie);
    }

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentMovie(@RequestParam int rentalDurationInWeeks, @RequestParam UUID id) throws IOException {
        Optional<Movie> movie = movieService.findMovieById(id);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rentalService.rentMovie(movie.get(), rentalDurationInWeeks, System.currentTimeMillis()));
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getRentals() throws IOException {
        return ResponseEntity.ok(rentalService.getRentals());
    }
}


