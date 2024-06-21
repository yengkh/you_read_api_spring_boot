package com.you_read_api.you_read_api.controller;

import com.you_read_api.you_read_api.entity.*;
import com.you_read_api.you_read_api.repository.BookTypeRepository;
import com.you_read_api.you_read_api.repository.BooksRepository;
import com.you_read_api.you_read_api.repository.CarocelPathRepository;
import com.you_read_api.you_read_api.service.BooksService;
import com.you_read_api.you_read_api.service.CarocelService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

@RestController
@CrossOrigin("*")
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final BooksRepository booksRepository;
    private final CarocelService carocelService;
    private final CarocelPathRepository carocelPathRepository;
    private final BookTypeRepository bookTypeRepository;
    @Autowired
    public BooksController(
            BooksService booksService,
            BooksRepository booksRepository,
            CarocelService carocelService,
            CarocelPathRepository carocelPathRepository,
            BookTypeRepository bookTypeRepository
    ) {
        this.booksService = booksService;
        this.booksRepository = booksRepository;
        this.carocelService = carocelService;
        this.carocelPathRepository = carocelPathRepository;
        this.bookTypeRepository = bookTypeRepository;
    }
    //Version 1
    @PostMapping("/add-new-books")
    public BooksEntity addNewBooks(
            @ModelAttribute
            BooksEntityDTO booksEntityDTO
    ) throws IOException {
        // Save pdf file
        MultipartFile file = booksEntityDTO.getFile();

        String storageFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();


            String uploadFileDir = "public/files/";
            Path uploadPath = Paths.get(uploadFileDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadFileDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

            String uploadDir = "public/images/";
            InputStream inputStream = file.getInputStream();
            PDDocument document = PDDocument.load(inputStream);

                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image = renderer.renderImage(0);

                // Save the first page of the PDF as an image file
                String imageFileName = UUID.randomUUID() + "_" +"image_for_first_page.png";
                Path imagePath = Paths.get(uploadDir + imageFileName);
                ImageIO.write(image, "png", imagePath.toFile());

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
        return booksRepository.save(booksEntity);
    }

    //Version 2
    @PostMapping("/add-new-book")
    public BooksEntity saveBooks(BooksEntityDTO booksEntityDTO) throws IOException {
        BooksEntity savedEntity = booksService.saveBooks(booksEntityDTO);
        // Save the entity to the database
        return booksRepository.save(savedEntity);
    }

    @GetMapping("/get")
    public List<BooksEntity> getBooks(){
        return booksRepository.findAll();
    }

    @PostMapping("/post-carocel")
    public CarocelPath postImage(CarocelPathDTO carocelPathDTO) throws IOException {
        CarocelPath post = carocelService.postImage(carocelPathDTO);

        return carocelPathRepository.save(post);
    }

    @GetMapping("/get-carocel")
    public List<CarocelPath> getImage(){
        return carocelPathRepository.findAll();
    }

    @PostMapping("/post-book-type")
    public BookType postBookType(
            @ModelAttribute
            BookTypeDTO bookTypeDTO
    ) throws IOException {
        Date time = new Date();

        MultipartFile image = bookTypeDTO.getImage();
        String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        String uploadDir = "public/booktypeimages/";
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath) ){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = image.getInputStream()){
            Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
        }

        BookType bookType = new BookType();
        bookType.setTime(time);
        bookType.setName(bookTypeDTO.getName());
        bookType.setImage(imageName);

        return bookTypeRepository.save(bookType);
    }

    @GetMapping("/get-book-type")
    public List<BookType> getAllBookType(){
        return bookTypeRepository.findAll();
    }

    @GetMapping("/find-books-by-type/{type}")
    public List<BooksEntity> getBooksByType(
            @PathVariable String type
    ) {
        return booksService.findByType(type);
    }

    @GetMapping("/search")
    public List<BooksEntity> searchBooks(
            @RequestParam(required = false)
            String name,
            @RequestParam(required = false)
            String author
    ) {
        return booksService.searchBooks(name, author);
    }

    @GetMapping("/get-book-by-name")
    public List<BooksEntity> getBookByName(
            @RequestParam(required = false)
            String name
    ){
        return  booksService.findByName(name);
    }
}
