package com.you_read_api.you_read_api.repository;

import com.you_read_api.you_read_api.entity.CarocelPath;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CarocelPathRepository extends JpaRepository<CarocelPath, UUID> {
}
