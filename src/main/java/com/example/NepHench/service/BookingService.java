package com.example.NepHench.service;

import com.example.NepHench.beans.BookingRequest;
import com.example.NepHench.model.Booking;
import com.example.NepHench.model.BookingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface BookingService {

    ResponseEntity<String> createBookingRequest(BookingRequest bookingRequest) throws IOException;
    ResponseEntity<String> updateAvailability(BookingRequest bookingRequest);


    //To accept the booking request
   @Transactional
   String acceptBookingRequest(Integer bookingId) throws IOException;

    List<Booking> findAll();

    @Transactional
    String rejectBookingRequest(Integer bookingId) throws IOException;

    List<Booking> getBookingsByServiceProviderId(Integer serviceProvider);

    List<Booking> getBookingsByCustomerId(Integer customer);

    List<BookingsService> getServicesByBookingId(Integer booking);

    void taskCompleted(Integer bookingId) throws IOException;
}
