package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.BookingRequest;
import com.example.NepHench.model.Booking;
import com.example.NepHench.model.BookingsService;
import com.example.NepHench.repository.BookingRepository;
import com.example.NepHench.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private  BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("")
    @ResponseBody
    public List<Booking> getAllbookings() {
        return bookingService.findAll();
    }

    @GetMapping("/{serviceProvider}")
    public ResponseEntity<List<Booking>> getBookingsByServiceProviderId(@PathVariable Integer serviceProvider) {
        List<Booking> bookings = bookingService.getBookingsByServiceProviderId(serviceProvider);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/services/{booking}")
    public ResponseEntity<List<BookingsService>> getServicesByBookingId(@PathVariable Integer booking) {
        List<BookingsService> bookings = bookingService.getServicesByBookingId(booking);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/customer/{customer}")
    public ResponseEntity<List<Booking>> getBookingsByCustomerId(@PathVariable Integer customer) {
        List<Booking> bookings = bookingService.getBookingsByCustomerId(customer);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createBookingRequest(@RequestBody BookingRequest bookingRequest) {
        try {
            // Validate the input data
            if (bookingRequest.getCustomer() == null || bookingRequest.getServiceProvider() == null ||
                    bookingRequest.getDate() == null || bookingRequest.getTime() == null) {
                ApiResponse response  = new ApiResponse("Invalid booking request data");
                return ResponseEntity.badRequest().body(response);
            }

            // Check service provider availability
            bookingService.updateAvailability(bookingRequest);

            // Create the booking request
            bookingService.createBookingRequest(bookingRequest);

            ApiResponse response = new ApiResponse("Booking request created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{bookingId}/accept")
    public ResponseEntity<ApiResponse> acceptBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.acceptBookingRequest(bookingId);
            ApiResponse response = new ApiResponse("Booking accepted");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse("Invalid booking ID");
            return ResponseEntity.badRequest().body(response);
        } catch (IllegalStateException e) {
            ApiResponse response = new ApiResponse("The booking is not in pending status");
            return ResponseEntity.badRequest().body(response);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<ApiResponse> rejectBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.rejectBookingRequest(bookingId);
            ApiResponse response = new ApiResponse("Booking rejected");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse("Invalid booking ID");
            return ResponseEntity.badRequest().body(response);
        } catch (IllegalStateException e) {
            ApiResponse response = new ApiResponse("The booking is not in pending status");
            return ResponseEntity.badRequest().body(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{bookingId}/progress")
    public Booking updateProgressStatus(@PathVariable Integer bookingId, @RequestParam String progressStatus) throws IOException {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setProgressStatus(progressStatus);

            if("completed".equalsIgnoreCase(progressStatus)){
                bookingService.taskCompleted(bookingId);
            }

            return bookingRepository.save(booking);
        }
        return null;
    }

}
