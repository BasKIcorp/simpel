package org.simpel.pumpingUnits.config;

import jakarta.annotation.PostConstruct;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirectoryInitializer {
    private final MaterialRepo materialRepo;

    private final String uploadDir = "uploads/";

    public DirectoryInitializer(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println(materialRepo.findAll());
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + uploadDir);
            } else {
                System.out.println("Directory already exists: " + uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory: " + uploadDir, e);
        }
    }
}