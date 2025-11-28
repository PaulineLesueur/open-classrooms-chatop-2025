package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping(value = "/api/rentals/{id}", consumes = "multipart/form-data")
    public Rental createRental(@ModelAttribute RentalRequest request) throws IOException {
        return rentalService.createRental(request);
    }

    @PutMapping("api/rentals/{id}")
    public Rental updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        return rentalService.updateRental(id, rental);
    }
}
