package com.you_read_api.you_read_api.repository;

import com.you_read_api.you_read_api.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, UUID> {
    List<BooksEntity> findByType(String type);
    List<BooksEntity> findByName(String name);
    List<BooksEntity> findByAuthor(String author);
    List<BooksEntity> findByNameAndAuthor(String name, String author);

    @Query("SELECT b FROM BooksEntity b WHERE LOWER(REPLACE(b.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))")
    List<BooksEntity> findByNameIgnoreCaseAndSpaces(@Param("name") String name);

    @Query("SELECT b FROM BooksEntity b WHERE LOWER(REPLACE(b.author, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:author, ' ', ''), '%'))")
    List<BooksEntity> findByAuthorIgnoreCaseAndSpaces(@Param("author") String author);
}
