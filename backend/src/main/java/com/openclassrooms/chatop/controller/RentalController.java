package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.DTO.RentalResponse;
import com.openclassrooms.chatop.service.RentalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    /**
     * Retrieves all rentals.
     *
     * @return an iterable list of RentalResponse DTOs representing all rentals
     */
    @GetMapping("/api/rentals")
    public Iterable<RentalResponse> getAllRentals() {
        return rentalService.getRentals();
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id the ID of the rental to retrieve
     * @return the RentalResponse DTO representing the requested rental
     */
    @GetMapping("/api/rentals/{id}")
    public RentalResponse getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    /**
     * Creates a new rental.
     *
     * <p>The request must be multipart/form-data to handle potential file uploads (e.g., pictures).</p>
     *
     * @param request the RentalRequest DTO containing rental details and optional files
     * @return the created RentalResponse DTO
     * @throws IOException if an error occurs during file handling
     */
    @PostMapping(value = "/api/rentals", consumes = "multipart/form-data")
    public RentalResponse createRental(@ModelAttribute RentalRequest request) throws IOException {
        return rentalService.createRental(request);
    }

    /**
     * Updates an existing rental.
     *
     * @param id the ID of the rental to update
     * @param rental the RentalRequest DTO with updated rental data
     * @return the updated RentalResponse DTO
     */
    @PutMapping("api/rentals/{id}")
    public RentalResponse updateRental(@PathVariable Long id, @ModelAttribute RentalRequest rental) {
        return rentalService.updateRental(id, rental);
    }
}
