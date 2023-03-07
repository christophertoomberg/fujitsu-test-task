package com.example.demo.services;

import com.example.demo.models.Movie;
import com.example.demo.models.Rental;
import com.example.demo.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getRentals() throws IOException {
        return rentalRepository.getRentals();
    }

    public Rental rentMovie(Movie movie, int rentalDurationInWeeks, long currentTime) throws IOException {
        double rentalPrice = movie.calculateRentalPrice(rentalDurationInWeeks, currentTime);
        Rental rental = new Rental(movie, rentalDurationInWeeks, rentalPrice);

        return rentalRepository.rentMovie(rental);
    }
}
