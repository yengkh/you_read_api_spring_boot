package com.you_read_api.you_read_api.entity;

import org.springframework.web.multipart.MultipartFile;

public class CarocelPathDTO {
    private MultipartFile image;

    public CarocelPathDTO(MultipartFile image) {
        this.image = image;
    }

    public CarocelPathDTO() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
