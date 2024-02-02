package com.example.NepHench.repository;

import com.example.NepHench.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByUserId(Integer userId);
}
