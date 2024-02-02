package com.example.NepHench.repository;

import com.example.NepHench.model.BookingsService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsServicesRepository extends JpaRepository<BookingsService, Integer> {
    List<BookingsService> findByBookingId(Integer booking);
}
