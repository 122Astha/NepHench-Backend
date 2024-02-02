package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.AmountRequest;
import com.example.NepHench.model.Amount;
import com.example.NepHench.model.Booking;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.AmountRepository;
import com.example.NepHench.repository.BookingRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.AmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AmountServiceImpl implements AmountService {

   @Autowired
   private  final AmountRepository amountRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BookingRepository bookingRepository;

    public AmountServiceImpl(AmountRepository amountRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.amountRepository = amountRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public ResponseEntity<String> submitAmount(AmountRequest amountRequest) {
        Optional<User> customer = userRepository.findById(amountRequest.getCustomerId());
        if (customer == null) {
            return ResponseEntity.badRequest().body("Invalid customer");
        }
        Optional<User> sp = userRepository.findById(amountRequest.getServiceProviderId());
        if (sp == null) {
            return ResponseEntity.badRequest().body("Invalid service provider");
        }
        Optional<Booking> bookingId = bookingRepository.findById(amountRequest.getServiceId());
        if (bookingId == null ) {
            return ResponseEntity.badRequest().body("Invalid booking");
        }

        Amount amount = new Amount();
        amount.setCustomerId(customer.get());
        amount.setServiceProviderId(sp.get());
        amount.setServiceId(bookingId.get());
        amount.setTotalAmount(amountRequest.getTotalAmount());

        amountRepository.save(amount);
        return  ResponseEntity.ok("Amount stored");
    }
}
