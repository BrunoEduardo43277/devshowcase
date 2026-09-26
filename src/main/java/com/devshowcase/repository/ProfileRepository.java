package com.devshowcase.repository;

import com.devshowcase.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
