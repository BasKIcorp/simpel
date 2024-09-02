package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorageService {
    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file, ParentInstallations installations) throws IOException {
        String directoryPath = uploadDir + installations.getTypeInstallations() + "/" + installations.getSubtype();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String uniqueFileName = UUID.randomUUID().toString() + " для установки " + installations.getTypeInstallations() + " " + installations.getSubtype() + " " + installations.getId() + ".jpg";
        String filePath = directoryPath + "/" + uniqueFileName;

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new IllegalArgumentException("Cannot read image file");
        }

        File outputFile = new File(filePath);
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return filePath;
    }



    public List<String> saveFiles(MultipartFile[] files, ParentInstallations installation) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            fileNames.add(saveFile(file, installation));
        }
        return fileNames;
    }
}
