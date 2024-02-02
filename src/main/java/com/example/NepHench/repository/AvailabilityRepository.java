package com.example.NepHench.repository;

import com.example.NepHench.model.Availability;
import com.example.NepHench.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    Availability findByServiceProviderIdAndDateAndTime(Integer serviceProviderId, LocalDate date, LocalTime time);

    @Modifying
    @Query("UPDATE Availability a SET a.availabilityStatus = :status " +
            "WHERE a.serviceProvider = :serviceProvider " +
            "AND a.date = :date " +
            "AND a.time = :time")
    void updateAvailabilityStatus(@Param("serviceProvider") User serviceProvider,
                                  @Param("date") LocalDate date,
                                  @Param("time") LocalTime time,
                                  @Param("status") String status);
}
