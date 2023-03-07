package com.example.demo.services;

import com.example.demo.models.Rental;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalDatabaseService {

    @Value("${useYaml}")
    private boolean useYaml;

    public void config(boolean useYaml) {
        this.useYaml = useYaml;
    }
    private List<Rental> rentals = new ArrayList<>();

    public List<Rental> getRentals() throws IOException {
        return loadRentals();
    }
    public Rental insertRentalForMovie(Rental rental) throws IOException {
        addItem(rental);
        return rental;
    }

    public void addItem(Rental rentalToBeAdded) throws IOException {
        List<Rental> loadedRentals = loadRentals();
        loadedRentals.add(rentalToBeAdded);
        rentals = loadedRentals;

        saveRentals();
        rentals.clear();
    }

    private List<Rental> loadRentals() throws IOException {
        if (useYaml) {
            return FileIOService.readRentalsFromYamlFile();
        } else {
            return FileIOService.readRentalsFromJsonFile();
        }
    }

    private void saveRentals() throws IOException {
        if (useYaml) {
            FileIOService.saveRentalsToYamlFile(rentals);
        } else {
            FileIOService.saveRentalsToJsonFile(rentals);
        }
    }
}
