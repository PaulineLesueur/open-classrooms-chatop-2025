package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.DTO.RentalResponse;
import com.openclassrooms.chatop.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public Iterable<RentalResponse> getAllRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/api/rentals/{id}")
    public RentalResponse getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping(value = "/api/rentals/{id}", consumes = "multipart/form-data")
    public RentalResponse createRental(@ModelAttribute RentalRequest request) throws IOException {
        return rentalService.createRental(request);
    }

    @PutMapping("api/rentals/{id}")
    public RentalResponse updateRental(@PathVariable Long id, @ModelAttribute RentalRequest rental) {
        return rentalService.updateRental(id, rental);
    }
}
