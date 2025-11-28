package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private FileService fileService;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(final Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public Rental createRental(RentalRequest request) throws IOException {
        String picturePath = fileService.save(request.getPicture());
        Rental rental = new Rental();

        rental.setOwnerId(1);
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(picturePath);
        rental.setDescription(request.getDescription());
        rental.setCreatedAt(LocalDate.now());

        return rentalRepository.save(rental);
    }

    public Rental updateRental(Long id, Rental updatedRental) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setName(updatedRental.getName());
        rental.setPrice(updatedRental.getPrice());
        rental.setSurface(updatedRental.getSurface());
        rental.setDescription(updatedRental.getDescription());
        rental.setUpdatedAt(LocalDate.now());

        return rentalRepository.save(rental);
    }
}
