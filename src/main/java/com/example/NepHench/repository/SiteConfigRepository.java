package com.example.NepHench.repository;

import com.example.NepHench.model.SiteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteConfigRepository extends JpaRepository<SiteConfig, Integer> {
}
