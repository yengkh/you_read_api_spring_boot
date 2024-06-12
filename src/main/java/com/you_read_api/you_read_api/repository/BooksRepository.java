package com.you_read_api.you_read_api.repository;

import com.you_read_api.you_read_api.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, UUID> {
    List<BooksEntity> findByType(String type);
    List<BooksEntity> findByName(String name);
    List<BooksEntity> findByAuthor(String author);
    List<BooksEntity> findByNameAndAuthor(String name, String author);
}
