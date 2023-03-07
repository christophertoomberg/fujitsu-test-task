package com.example.demo;

import com.example.demo.models.Movie;
import com.example.demo.models.MovieBuilder;
import com.example.demo.services.FileIOService;
import com.example.demo.services.MovieDataBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class MovieDatabaseServiceTests {
    @Autowired
    private MovieDataBaseService movieDatabaseService;

    public enum MovieAge {
        OLD,
        REGULAR,
        REGULAR2,
        NEW
    }

    public static Movie createMovieByAgeWithAge(MovieAge movieAge) {
        switch (movieAge) {
            case OLD -> {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.set(2001, Calendar.JULY, 13);

                return new MovieBuilder()
                        .setMovieName("Shrek")
                        .setReleaseDate(releaseDate)
                        .setActors(new ArrayList<>(Arrays.asList("Mike Myers", "Eddie Murphy", "Cameron Diaz")))
                        .setCategory(Movie.MovieCategory.ANIMATION)
                        .setDescription("A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.")
                        .createMovie();
            }
            case REGULAR -> {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.set(2021, Calendar.NOVEMBER, 21);

                return new MovieBuilder()
                        .setMovieName("House of Gucci")
                        .setReleaseDate(releaseDate)
                        .setActors(new ArrayList<>(Arrays.asList("Lady Gaga", "Adam Driver", "Al Pacino")))
                        .setCategory(Movie.MovieCategory.DRAMA)
                        .setDescription("When Patrizia Reggiani, an outsider from humble beginnings, marries into the Gucci family, her unbridled ambition begins to unravel their legacy and triggers a reckless spiral of betrayal, decadence, revenge, and ultimately...murder.")
                        .createMovie();
            }

            case REGULAR2 -> {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.set(2020, Calendar.MARCH, 21);

                return new MovieBuilder()
                        .setMovieName("Dolittle")
                        .setReleaseDate(releaseDate)
                        .setActors(new ArrayList<>(Arrays.asList("Robert Downey Jr.", "An", "Al Pacino")))
                        .setCategory(Movie.MovieCategory.DRAMA)
                        .setDescription("A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.")
                        .createMovie();
            }


            case NEW -> {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.set(2022, Calendar.OCTOBER, 14);

                return new MovieBuilder()
                        .setMovieName("Triangle of Sadness")
                        .setReleaseDate(releaseDate)
                        .setActors(new ArrayList<>(Arrays.asList("Harris Dickinson", "Charlbi Dean", "Dolly de Leon")))
                        .setCategory(Movie.MovieCategory.COMEDY)
                        .setDescription("A fashion model celebrity couple join an eventful cruise for the super-rich.")
                        .createMovie();
            }

            default -> {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.set(2003, Calendar.NOVEMBER, 21);

                return new MovieBuilder()
                        .setMovieName("Love Actually")
                        .setReleaseDate(releaseDate)
                        .setActors((new ArrayList<>(Arrays.asList("Hugh Grant", "Martine McCutcheon", "Liam Neeson"))))
                        .setCategory(Movie.MovieCategory.ROMANCE)
                        .setDescription("Follows the lives of eight very different couples in dealing with their love lives in various loosely interrelated tales all set during a frantic month before Christmas in London, England.")
                        .createMovie();
            }
        }
    }

    @Test
    public void insertMoviesToJsonAndYamlAndUseJson() throws IOException {
        FileIOService.flushDatabases();
        movieDatabaseService.config(false);

        List<Movie> movies = Arrays.asList(
                createMovieByAgeWithAge(MovieAge.OLD),
                createMovieByAgeWithAge(MovieAge.REGULAR),
                createMovieByAgeWithAge(MovieAge.REGULAR2),
                createMovieByAgeWithAge(MovieAge.NEW));

        movieDatabaseService.addItems(movies);

        movieDatabaseService.config(true);
        movieDatabaseService.addItems(movies);

        movieDatabaseService.config(false);
    }

    @Test
    public void insertMoviesIntoJSONFile() throws IOException {
        movieDatabaseService.config(false);
        FileIOService.flushDatabases();

        List<Movie> createdMovies = Arrays.asList(createMovieByAgeWithAge(MovieAge.OLD),
                createMovieByAgeWithAge(MovieAge.REGULAR));

        movieDatabaseService.addItems(createdMovies);

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(createdMovies.size());
    }

    @Test
    public void insertSingleMovieToJSONFile() throws IOException {
        movieDatabaseService.config(false);
        FileIOService.flushDatabases();


        Movie createdMovie = createMovieByAgeWithAge(MovieAge.OLD);
        movieDatabaseService.addItem(createdMovie);

        assertThat(movieDatabaseService.getMovies().get(0).getId()).isEqualTo(createdMovie.getId());
    }

    @Test
    public void insertMoviesIntoYAMLFile() throws IOException {
        movieDatabaseService.config(true);
        FileIOService.flushDatabases();

        List<Movie> createdMovies = Arrays.asList(createMovieByAgeWithAge(MovieAge.OLD),
                createMovieByAgeWithAge(MovieAge.REGULAR));

        movieDatabaseService.addItems(createdMovies);

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(createdMovies.size());
    }

    @Test
    public void updateMovieInJSONFile() throws IOException {
        movieDatabaseService.config(false);
        FileIOService.flushDatabases();

        Movie createdMovie = createMovieByAgeWithAge(MovieAge.OLD);
        movieDatabaseService.addItem(createdMovie);

        Calendar newReleaseDate = Calendar.getInstance();
        newReleaseDate.set(2004, Calendar.JUNE, 30);

        Movie newMovie = new MovieBuilder()
                .setMovieName("Shrek 3")
                .setReleaseDate(newReleaseDate)
                .setActors(new ArrayList<>(Arrays.asList("Mike Myers", "Eddie Murphy", "Cameron Diaz")))
                .setCategory(Movie.MovieCategory.ANIMATION)
                .setDescription("Shrek and Fiona travel to the Kingdom of Far Far Away, where Fiona's parents are King and Queen, to celebrate their marriage. When they arrive, they find they are not as welcome as they thought they would be.")
                .createMovie();
        movieDatabaseService.updateItem(createdMovie.getId(), newMovie);

        assertThat(movieDatabaseService.getMovies().get(0).getId()).isEqualTo(createdMovie.getId());
        assertThat(movieDatabaseService.getMovies().get(0).getMovieName()).isEqualTo(newMovie.getMovieName());
    }

    @Test
    public void updateMovieInYamlFile() throws IOException {
        movieDatabaseService.config(true);
        FileIOService.flushDatabases();

        Movie createdMovie = createMovieByAgeWithAge(MovieAge.OLD);
        movieDatabaseService.addItem(createdMovie);

        Calendar newReleaseDate = Calendar.getInstance();
        newReleaseDate.set(2004, Calendar.JUNE, 30);

        Movie newMovie = new MovieBuilder()
                .setMovieName("Shrek 3")
                .setReleaseDate(newReleaseDate)
                .setActors(new ArrayList<>(Arrays.asList("Mike Myers", "Eddie Murphy", "Cameron Diaz")))
                .setCategory(Movie.MovieCategory.ANIMATION)
                .setDescription("Shrek and Fiona travel to the Kingdom of Far Far Away, where Fiona's parents are King and Queen, to celebrate their marriage. When they arrive, they find they are not as welcome as they thought they would be.")
                .createMovie();
        movieDatabaseService.updateItem(createdMovie.getId(), newMovie);

        assertThat(movieDatabaseService.getMovies().get(0).getId()).isEqualTo(createdMovie.getId());
        assertThat(movieDatabaseService.getMovies().get(0).getMovieName()).isEqualTo(newMovie.getMovieName());
    }

    @Test
    public void deleteMovieInJsonFile() throws IOException {
        movieDatabaseService.config(false);
        FileIOService.flushDatabases();

        Movie createdMovie = createMovieByAgeWithAge(MovieAge.NEW);
        movieDatabaseService.addItem(createdMovie);

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(1);

        movieDatabaseService.deleteItem(createdMovie.getId());

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(0);
    }


    @Test
    public void deleteMovieInYamlFile() throws IOException {
        movieDatabaseService.config(true);
        FileIOService.flushDatabases();

        Movie createdMovie = createMovieByAgeWithAge(MovieAge.NEW);
        movieDatabaseService.addItem(createdMovie);

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(1);

        movieDatabaseService.deleteItem(createdMovie.getId());

        assertThat(movieDatabaseService.getMovies().size()).isEqualTo(0);
    }
}
