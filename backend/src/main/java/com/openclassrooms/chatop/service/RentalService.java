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

    public Iterable<RentalResponse> getRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalResponse> dtoList = StreamSupport.stream(rentals.spliterator(), false)
                .map(this::convertToRentalResponse)
                .collect(Collectors.toList());

        return dtoList;
    }

    public RentalResponse getRentalById(final Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return convertToRentalResponse(rental);
    }

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
