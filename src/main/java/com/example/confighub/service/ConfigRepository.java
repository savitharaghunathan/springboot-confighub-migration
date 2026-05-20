package com.example.confighub.service;

import com.example.confighub.model.ConfigEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntry, Long> {
    Optional<ConfigEntry> findByKey(String key);
    List<ConfigEntry> findByActiveTrue();
    boolean existsByKey(String key);
}
