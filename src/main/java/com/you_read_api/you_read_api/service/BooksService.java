package com.you_read_api.you_read_api.service;

import com.you_read_api.you_read_api.entity.*;
import com.you_read_api.you_read_api.repository.BooksRepository;
import com.you_read_api.you_read_api.repository.CarocelPathRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final CarocelPathRepository carocelPathRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository, CarocelPathRepository carocelPathRepository) {
        this.booksRepository = booksRepository;
        this.carocelPathRepository = carocelPathRepository;
    }

    public BooksEntity saveBooks(
            @ModelAttribute
            BooksEntityDTO booksEntityDTO
    ) throws IOException {
        Date createTime = new Date();
        // Save pdf file
        MultipartFile image = booksEntityDTO.getFile();

        String storageFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();


        String uploadFileDir = "public/files/";
        Path uploadPath = Paths.get(uploadFileDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = image.getInputStream()){
            Files.copy(inputStream, Paths.get(uploadFileDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
        }

        String uploadDir = "public/images/";
        InputStream inputStream = image.getInputStream();
        PDDocument document = PDDocument.load(inputStream);

        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage imageB = renderer.renderImage(0);

        // Save the first page of the PDF as an image file
        String imageFileName = UUID.randomUUID() + "_" + image.getOriginalFilename() + "image_for_first_page.png";
        Path imagePath = Paths.get(uploadDir + imageFileName);
        ImageIO.write(imageB, "png", imagePath.toFile());

        // Save the image file path to the database
        String imagePathStr = imageFileName;
        // Save imagePathStr to your PDFFile entity or wherever you're storing the file path in your database

        BooksEntity booksEntity = new BooksEntity();
        booksEntity.setName(booksEntityDTO.getName());
        booksEntity.setAuthor(booksEntityDTO.getAuthor());
        booksEntity.setDesc(booksEntityDTO.getDesc());
        booksEntity.setType(booksEntityDTO.getType());
        booksEntity.setFile(storageFileName);
        booksEntity.setImage(imagePathStr);
        booksEntity.setCreateTime(createTime);

        return booksEntity;
    }

    public List<BooksEntity> findByType(String type) {
        return booksRepository.findByType(type);
    }

    public List<BooksEntity> searchBooks(String name, String author) {
        if (name != null || author != null) {
            return booksRepository.findByNameAndAuthor(name, author);
        } else if (name != null) {
            return booksRepository.findByName(name);
        } else if (author != null) {
            return booksRepository.findByAuthor(author);
        } else {
            return booksRepository.findAll();
        }
    }

    public List<BooksEntity> findBookByName(String name) {
        return booksRepository.findByName(name);
    }
}
