package com.example.demo.repositories;

import com.example.demo.models.Rental;
import com.example.demo.services.RentalDatabaseService;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class RentalRepository {

    private final RentalDatabaseService rentalDatabaseService;

    public RentalRepository(RentalDatabaseService rentalDatabaseService) {
        this.rentalDatabaseService = rentalDatabaseService;
    }

    public Rental rentMovie(Rental rental) throws IOException {
        return rentalDatabaseService.insertRentalForMovie(rental);
    }

    public List<Rental> getRentals() throws IOException {
        return rentalDatabaseService.getRentals();
    }
}
