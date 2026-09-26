package com.devshowcase.repository;

import com.devshowcase.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    boolean existsByNameIgnoreCase(String name);
}
