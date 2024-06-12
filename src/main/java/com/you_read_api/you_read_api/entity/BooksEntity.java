package com.you_read_api.you_read_api.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class BooksEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(length = 250)
    private String name;
    @Column(length = 250)
    private String author;
    @Column(length = 250)
    private String type;
    @Column(length = 1000, name = "description")
    private String desc;
    private String file;
    private String image;
    private Date createTime;
    private String downloaded;

    public BooksEntity() {
    }

    public BooksEntity(
            UUID id,
            String name,
            String author,
            String type,
            String desc,
            String file,
            String image,
            Date createTime,
            String downloaded
    ) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.type = type;
        this.desc = desc;
        this.file = file;
        this.image = image;
        this.createTime = createTime;
        this.downloaded = downloaded;
    }

    public BooksEntity(
            String name,
            String author,
            String type,
            String desc,
            String file,
            String image,
            Date createTime,
            String downloaded
    ) {
        this.name = name;
        this.author = author;
        this.type = type;
        this.desc = desc;
        this.file = file;
        this.image = image;
        this.createTime = createTime;
        this.downloaded = downloaded;
    }

    public String getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(String downloaded) {
        this.downloaded = downloaded;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
