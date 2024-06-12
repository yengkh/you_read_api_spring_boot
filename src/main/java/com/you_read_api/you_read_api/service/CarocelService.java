package com.you_read_api.you_read_api.service;

import com.you_read_api.you_read_api.entity.CarocelPath;
import com.you_read_api.you_read_api.entity.CarocelPathDTO;
import com.you_read_api.you_read_api.repository.CarocelPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@Service
public class CarocelService {
    private final CarocelPathRepository carocelPathRepository;

    @Autowired
    public CarocelService(CarocelPathRepository carocelPathRepository) {
        this.carocelPathRepository = carocelPathRepository;
    }

    public CarocelPath postImage(
            @ModelAttribute
            CarocelPathDTO carocelPathDTO
    ) throws IOException {
        Date uploadDate = new Date();
        MultipartFile file = carocelPathDTO.getImage();
        String imagePath = UUID.randomUUID() + "_" + file.getOriginalFilename();

        String imagePathDir = "public/carocelImages/";
        Path uploadPath = Paths.get(imagePathDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, Paths.get(imagePathDir + imagePath), StandardCopyOption.REPLACE_EXISTING);
        }

        CarocelPath carocelPath = new CarocelPath();
        carocelPath.setImage(imagePath);
        carocelPath.setUploadDate(uploadDate);

        return carocelPathRepository.save(carocelPath);
    }
}
