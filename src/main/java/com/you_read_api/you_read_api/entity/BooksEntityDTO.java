package com.you_read_api.you_read_api.entity;

import org.springframework.web.multipart.MultipartFile;

public class BooksEntityDTO {
    private String name;
    private String desc;
    private String type;
    private String author;
    private MultipartFile file;
    private MultipartFile image;
    private String downloaded;

    public BooksEntityDTO() {
    }

    public BooksEntityDTO(
            String name,
            String desc,
            String type,
            String author,
            MultipartFile file,
            MultipartFile image,
            String downloaded
    ) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.author = author;
        this.file = file;
        this.image = image;
        this.downloaded = downloaded;
    }

    public String getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(String downloaded) {
        this.downloaded = downloaded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
