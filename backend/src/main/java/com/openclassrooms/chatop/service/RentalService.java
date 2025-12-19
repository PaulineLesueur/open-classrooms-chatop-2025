package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.DTO.RentalResponse;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    /**
     * Converts a Rental entity to a RentalResponse DTO.
     *
     * @param rental the rental entity to convert
     * @return the corresponding RentalResponse DTO
     */
    private RentalResponse convertToRentalResponse(Rental rental) {
        RentalResponse dto = new RentalResponse();

        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture("http://localhost:3001/uploads/" + rental.getPicture());
        dto.setDescription(rental.getDescription());
        if(rental.getOwner() != null) {
            dto.setOwnerId(rental.getOwner().getId());
        }
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());

        return dto;
    }

    /**
     * Retrieves all rentals and converts them to DTOs.
     *
     * @return an iterable of RentalResponse DTOs
     */
    public Iterable<RentalResponse> getRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalResponse> dtoList = StreamSupport.stream(rentals.spliterator(), false)
                .map(this::convertToRentalResponse)
                .collect(Collectors.toList());

        return dtoList;
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id the ID of the rental to retrieve
     * @return the RentalResponse DTO for the specified rental
     * @throws RuntimeException if no rental is found with the given ID
     */
    public RentalResponse getRentalById(final Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return convertToRentalResponse(rental);
    }

    /**
     * Creates a new rental using the provided request data and saves it.
     * The picture is saved using the FileService.
     *
     * @param request the RentalRequest DTO containing rental details
     * @return the created rental as a RentalResponse DTO
     * @throws IOException if an error occurs while saving the picture
     */
    public RentalResponse createRental(RentalRequest request) throws IOException {
        String picturePath = fileService.save(request.getPicture());
        Rental rental = new Rental();
        User currentUser = userService.getCurrentUser();

        rental.setOwner(currentUser);
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(picturePath);
        rental.setDescription(request.getDescription());
        rental.setCreatedAt(LocalDate.now());

        Rental savedRental = rentalRepository.save(rental);

        return convertToRentalResponse(savedRental);
    }

    /**
     * Updates an existing rental with new data.
     *
     * @param id the ID of the rental to update
     * @param updatedRental the new rental data from RentalRequest DTO
     * @return the updated rental as a RentalResponse DTO
     * @throws RuntimeException if no rental is found with the given ID
     */
    public RentalResponse updateRental(Long id, RentalRequest updatedRental) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setName(updatedRental.getName());
        rental.setPrice(updatedRental.getPrice());
        rental.setSurface(updatedRental.getSurface());
        rental.setDescription(updatedRental.getDescription());
        rental.setUpdatedAt(LocalDate.now());

        Rental savedRental = rentalRepository.save(rental);

        return convertToRentalResponse(savedRental);
    }
}