package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.RentalRequest;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private FileService fileService;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(final Long id) {
        return rentalRepository.findById(id);
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
}
