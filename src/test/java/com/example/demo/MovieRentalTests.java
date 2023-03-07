package com.example.demo;

import com.example.demo.models.Movie;
import com.example.demo.services.FileIOService;
import com.example.demo.services.RentalDatabaseService;
import com.example.demo.services.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MovieRentalTests {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalDatabaseService rentalDatabaseService;
    @Test
    public void rentOldMovieUseJSON() throws IOException {
        rentalDatabaseService.config(false);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.OLD);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 1.99);
    }

    @Test
    public void rentOldMovieUseYAML() throws IOException {
        rentalDatabaseService.config(true);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.OLD);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 1.99);
    }

    @Test
    public void rentRegularMovieUseJSON() throws IOException {
        rentalDatabaseService.config(false);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.REGULAR);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 3.49);
    }

    @Test
    public void rentRegularMovieUseYAML() throws IOException {
        rentalDatabaseService.config(true);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.REGULAR);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 3.49);
    }

    @Test
    public void rentNewMovieUseJSON() throws IOException {
        rentalDatabaseService.config(false);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.NEW);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 2.0);
    }

    @Test
    public void rentNewMovieUseYAML() throws IOException {
        rentalDatabaseService.config(true);
        FileIOService.flushDatabases();
        Movie oldMovie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.NEW);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(oldMovie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(4 * 5.0);
    }

    @Test
    public void rentRegularMovieThatBecomesOldMovie() throws IOException {
        rentalDatabaseService.config(false);
        FileIOService.flushDatabases();
        Movie movie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.REGULAR2);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.MARCH, 7);

        rentalService.rentMovie(movie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(2 * 3.49 + 2 * 1.99);
    }

    @Test
    public void rentNewMovieThatBecomesRegularMovie() throws IOException {
        rentalDatabaseService.config(false);
        FileIOService.flushDatabases();
        Movie movie = MovieDatabaseServiceTests.createMovieByAgeWithAge(MovieDatabaseServiceTests.MovieAge.NEW);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(2023, Calendar.OCTOBER, 7);

        rentalService.rentMovie(movie, 4, currentDate.getTimeInMillis());

        assertThat(rentalService.getRentals().get(0).getRentalPrice()).isEqualTo(1 * 5.0 + 3 * 3.49);
    }
}
