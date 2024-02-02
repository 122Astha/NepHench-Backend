package com.example.NepHench.repository;

import com.example.NepHench.model.Nephenchservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Nephenchservice, Integer> {
   List<Nephenchservice> findAllByIdIn(List<Integer> ids);
}
