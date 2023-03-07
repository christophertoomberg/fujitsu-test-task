package com.example.demo;

import com.example.demo.controllers.MoviesController;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieDAO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebLayerTest {


    @Autowired
    private MoviesController controller;


    // RUN insertMoviesToJsonAndYamlAndUseJson() (first method) in MovieDatabaseServiceTests before running the tests here.
    @Test
    @Order(1)
    public void getMovies() throws IOException {
        assertThat(Objects.requireNonNull(controller.getAllMovies().getBody()).size()).isEqualTo(4);
        assertThat(controller.getAllMovies().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void getMovieById() throws IOException {
        assertThat(Objects.requireNonNull(controller.getMovieById(Objects.requireNonNull(controller.getAllMovies().getBody()).get(0).getId()).getBody()).getMovieName()).isEqualTo("Shrek");
        assertThat(controller.getAllMovies().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void searchForMovieByName() throws IOException {
        assertThat(Objects.requireNonNull(controller.searchForMovieByName("House of Gucci").getStatusCode())).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    public void searchForMovieByNameMovieNotFound() throws IOException {
        assertThat(Objects.requireNonNull(controller.searchForMovieByName("Willy wonka").getStatusCode())).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(5)
    public void filterMoviesByCategory() throws IOException {
        assertThat(Objects.requireNonNull(Objects.requireNonNull(controller.filterMoviesByCategory(Movie.MovieCategory.DRAMA)).getBody()).size()).isEqualTo(2);
    }

    @Test
    @Order(6)
    public void addMovie() throws IOException {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(2001, Calendar.JULY, 13);
        MovieDAO movieDAO = new MovieDAO(
                "Shrek 3",
                "bla bla bla",
                new ArrayList<>(Arrays.asList("actor 2", "actor 3")),
                releaseDate,
                Movie.MovieCategory.ANIMATION);
        controller.addMovie(movieDAO);

        assertThat(Objects.requireNonNull(controller.getAllMovies().getBody()).size()).isEqualTo(5);
    }
    @Test
    @Order(7)
    public void addMovies() throws IOException {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(2001, Calendar.JULY, 13);
        MovieDAO movieDAO = new MovieDAO(
                "Shrek 3",
                "bla bla bla",
                new ArrayList<>(Arrays.asList("actor 2", "actor 3")),
                releaseDate,
                Movie.MovieCategory.ANIMATION);

        MovieDAO movieDAO1 = new MovieDAO(
                "Shrek ",
                "bla bla bla",
                new ArrayList<>(Arrays.asList("actor 2", "actor 3")),
                releaseDate,
                Movie.MovieCategory.ANIMATION);


        List<MovieDAO> movies = new ArrayList<>();

        movies.add(movieDAO);
        movies.add(movieDAO1);

        controller.addMovies(movies);

        assertThat(Objects.requireNonNull(controller.getAllMovies().getBody()).size()).isEqualTo(7);
    }

    @Test
    @Order(8)
    public void deleteMovie() throws IOException {
        controller.deleteMovieById(Objects.requireNonNull(controller.getAllMovies().getBody()).get(0).getId());

        assertThat(Objects.requireNonNull(controller.getAllMovies().getBody()).size()).isEqualTo(6);
    }

    @Test
    @Order(9)
    public void updateMovie() throws IOException {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(2001, Calendar.JULY, 13);
        MovieDAO movieDAO = new MovieDAO(
                "Shrek 12",
                "bla bla bla",
                new ArrayList<>(Arrays.asList("actor 2", "actor 3")),
                releaseDate,
                Movie.MovieCategory.ANIMATION);

        UUID id = Objects.requireNonNull(controller.getAllMovies().getBody()).get(0).getId();
        controller.updateMovieById(id, movieDAO);
        assertThat(controller.getAllMovies().getBody().get(controller.getAllMovies().getBody().size() - 1).getId()).isEqualTo(id);
    }

    @Test
    @Order(10)
    public void rentMovie() throws IOException {
        controller.rentMovie(6, Objects.requireNonNull(controller.getAllMovies().getBody()).get(0).getId());

        assertThat(Objects.requireNonNull(controller.getRentals().getBody()).size()).isEqualTo(1);
    }
}