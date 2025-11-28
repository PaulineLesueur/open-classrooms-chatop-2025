package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/api/rentals/{id}")
    public Optional<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }
}
