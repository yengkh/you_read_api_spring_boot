package com.you_read_api.you_read_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class CarocelPath {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String image;
    private Date uploadDate;

    public CarocelPath(UUID id, String image, Date uploadDate) {
        this.id = id;
        this.image = image;
        this.uploadDate = uploadDate;
    }

    public CarocelPath(String image, Date uploadDate) {
        this.image = image;
        this.uploadDate = uploadDate;
    }

    public CarocelPath() {
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
