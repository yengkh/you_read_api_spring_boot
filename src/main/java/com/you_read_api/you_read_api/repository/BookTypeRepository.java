package com.you_read_api.you_read_api.repository;

import com.you_read_api.you_read_api.entity.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, UUID> {
}
