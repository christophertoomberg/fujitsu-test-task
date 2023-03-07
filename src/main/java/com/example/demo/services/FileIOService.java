package com.example.demo.services;

import com.example.demo.models.Movie;
import com.example.demo.models.Rental;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOService {

    private static final String JSON_MOVIES_FILEPATH = "src/main/resources/databases/json/movies.json";
    private static final String YAML_MOVIES_FILEPATH = "src/main/resources/databases/yaml/movies.yaml";
    private static final String JSON_RENTALS_FILEPATH = "src/main/resources/databases/json/rentals.json";
    private static final String YAML_RENTALS_FILEPATH = "src/main/resources/databases/yaml/rentals.yaml";

    private static final ObjectMapper jsonMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());

    // read list of movies from JSON file
    public static List<Movie> readMoviesFromJsonFile() throws IOException {
        return jsonMapper.readValue(new File(JSON_MOVIES_FILEPATH), new TypeReference<List<Movie>>() {});
    }

    // save list of movies to JSON file
    public static void saveMoviesToJsonFile(List<Movie> movies) throws IOException {
        jsonMapper.writeValue(new File(JSON_MOVIES_FILEPATH), movies);
    }

    // read list of movies from YAML file
    public static List<Movie> readMoviesFromYamlFile() throws IOException {
        return yamlMapper.readValue(new File(YAML_MOVIES_FILEPATH), new TypeReference<List<Movie>>() {});
    }

    // save list of movies to YAML file
    public static void saveMoviesToYamlFile(List<Movie> movies) throws IOException {
        yamlMapper.writeValue(new File(YAML_MOVIES_FILEPATH), movies);
    }

    // read list of rentals from JSON file
    public static List<Rental> readRentalsFromJsonFile() throws IOException {
        return jsonMapper.readValue(new File(JSON_RENTALS_FILEPATH), new TypeReference<List<Rental>>() {});
    }

    // save list of rentals to JSON file
    public static void saveRentalsToJsonFile(List<Rental> rentals) throws IOException {
        jsonMapper.writeValue(new File(JSON_RENTALS_FILEPATH), rentals);
    }

    // read list of rentals from YAML file
    public static List<Rental> readRentalsFromYamlFile() throws IOException {
        return yamlMapper.readValue(new File(YAML_RENTALS_FILEPATH), new TypeReference<List<Rental>>() {});
    }

    // save list of rentals to YAML file
    public static void saveRentalsToYamlFile(List<Rental> rentals) throws IOException {
        yamlMapper.writeValue(new File(YAML_RENTALS_FILEPATH), rentals);
    }

    public static void flushDatabases() throws IOException {
        jsonMapper.writeValue(new File(JSON_MOVIES_FILEPATH), new ArrayList<>());
        jsonMapper.writeValue(new File(JSON_RENTALS_FILEPATH), new ArrayList<>());
        yamlMapper.writeValue(new File(YAML_MOVIES_FILEPATH), new ArrayList<>());
        yamlMapper.writeValue(new File(YAML_RENTALS_FILEPATH), new ArrayList<>());
    }
}