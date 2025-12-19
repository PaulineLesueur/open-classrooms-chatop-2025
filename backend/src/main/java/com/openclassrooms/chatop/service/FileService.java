package com.openclassrooms.chatop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    String uploadDir = "uploads/";

    public String save(MultipartFile file) throws IOException {
        Path folder = Paths.get(uploadDir);

        if(!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = folder.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }
}
