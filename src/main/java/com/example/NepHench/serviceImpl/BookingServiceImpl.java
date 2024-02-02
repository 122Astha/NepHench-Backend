package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.BookingRequest;
import com.example.NepHench.exception.AvailabilityStatusException;
import com.example.NepHench.model.*;
import com.example.NepHench.repository.*;
import com.example.NepHench.service.BookingService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BookingsServicesRepository bookingsServicesRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private  UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, AvailabilityRepository availabilityRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> updateAvailability(BookingRequest bookingRequest) {
        Optional<User> sp = userRepository.findById(bookingRequest.getServiceProvider());
        if (sp == null) {
            return ResponseEntity.badRequest().body("Invalid service provider");
        }
        // Retrieve the existing availability record for the specified service provider, date, and time
        Availability availability = availabilityRepository.findByServiceProviderIdAndDateAndTime(bookingRequest.getServiceProvider(), bookingRequest.getDate(), bookingRequest.getTime());

        ///changing the code here
        if (availability != null) {

            if (availability.getAvailabilityStatus().equals("booked")) {
                throw new AvailabilityStatusException("The service provider is already booked at the specified date and time.");
            }
            // Update the availability status to "available"
//            availability.setAvailabilityStatus("available");
            availabilityRepository.save(availability);
        } else {
            // Create a new availability record with the default status "available"
            availability = new Availability();
            availability.setServiceProvider(sp.get());
            availability.setDate(bookingRequest.getDate());
            availability.setTime(bookingRequest.getTime());
            availabilityRepository.save(availability);
        }
        return null;
    }

   @Override
   @Transactional
    public ResponseEntity<String> createBookingRequest(BookingRequest bookingRequest) throws IOException {

       Optional<User> customer = userRepository.findById(bookingRequest.getCustomer());
       if (customer == null) {
           return ResponseEntity.badRequest().body("Invalid customer");
       }
       Optional<User> sp = userRepository.findById(bookingRequest.getServiceProvider());
       if (sp == null) {
           return ResponseEntity.badRequest().body("Invalid service provider");
       }
       HttpClient httpClient = HttpClientBuilder.create().build();
       HttpPost httpRequest = new HttpPost("https://exp.host/--/api/v2/push/send");

       httpRequest.setHeader("Content-Type", "application/json");

       String payload = "{"
               + "\"to\": \"" + sp.get().getDeviceToken() + "\","
               + "\"title\": \"" + "Booking Request" + "\","
               + "\"body\": \"" + "You have a new booking request" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity entity = new StringEntity(payload);
       httpRequest.setEntity(entity);

       // Send the request and retrieve the response
       HttpResponse response = httpClient.execute(httpRequest);

       // Handle the response
       int statusCode = response.getStatusLine().getStatusCode();
       String responseString = EntityUtils.toString(response.getEntity());


       //For storing the notifications of each user
       HttpClient notfhttpClient = HttpClientBuilder.create().build();
       HttpPost notification = new HttpPost("http://localhost:9096/api/notifications");

       notification.setHeader("Content-Type", "application/json");
       String data = "{"
               + "\"user\": \"" + sp.get().getId() + "\","
               + "\"content\": \"" + "You have a new booking request" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity notfentity = new StringEntity(data);
       notification.setEntity(notfentity);

       // Send the request and retrieve the response
       HttpResponse notfresponse = notfhttpClient.execute(notification);

       // Handle the response
       int notfstatusCode = notfresponse.getStatusLine().getStatusCode();
       String notfresponseString = EntityUtils.toString(notfresponse.getEntity());

        // Create a new booking record with pending status
        Booking booking = new Booking();
//       LocalTime bookingTime = bookingRequest.getTime();
        booking.setCustomer(customer.get());
        booking.setServiceProvider(sp.get());
        booking.setDate(bookingRequest.getDate());
        booking.setTime(bookingRequest.getTime());
        booking.setStatus("pending");
        booking.setProgressStatus("pending");

        bookingRepository.save(booking);
       if (!bookingRequest.getSelectedServices().isEmpty()) {
           for (Integer serviceId : bookingRequest.getSelectedServices()) {
               Nephenchservice service = serviceRepository.findById(serviceId).orElse(null);
               if (service != null) {
                   BookingsService bookingsServices = new BookingsService();
                   bookingsServices.setBooking(booking);
                   bookingsServices.setService(service);
                   bookingsServicesRepository.save(bookingsServices);
               }
           }
       }
       else {
           return ResponseEntity.ok("Success");
       }
       return ResponseEntity.ok("Success");
   }

   //To accept the booking request
   @Override
   @Transactional
   public String acceptBookingRequest(Integer bookingId) throws IOException {
       Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
       Optional<User> customer = userRepository.findById(booking.getCustomer().getId());

       if (!booking.getStatus().equals("pending")) {
           throw new IllegalStateException("The booking is not in pending status");
       }
       if (booking.getStatus().equals("accepted")) {
           throw new IllegalStateException("The booking is already accepted");
       }
       HttpClient httpClient = HttpClientBuilder.create().build();
       HttpPost httpRequest = new HttpPost("https://exp.host/--/api/v2/push/send");

       httpRequest.setHeader("Content-Type", "application/json");

       String payload = "{"
               + "\"to\": \"" + customer.get().getDeviceToken() + "\","
               + "\"title\": \"" + "NepHench" + "\","
               + "\"body\": \"" + "Dear " + customer.get().getUsername()+ ", Your booking request has been accepted. Thank you!!" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity entity = new StringEntity(payload);
       httpRequest.setEntity(entity);
       // Send the request and retrieve the response
       HttpResponse response = httpClient.execute(httpRequest);

       // Handle the response
       int statusCode = response.getStatusLine().getStatusCode();
       String responseString = EntityUtils.toString(response.getEntity());

       //For storing the notifications of each user
       HttpClient notfhttpClient = HttpClientBuilder.create().build();
       HttpPost notification = new HttpPost("http://localhost:9096/api/notifications");

       notification.setHeader("Content-Type", "application/json");
       String data = "{"
               + "\"user\": \"" + customer.get().getId() + "\","
               + "\"content\": \"" + "Dear " + customer.get().getUsername()+ ", Your booking request has been accepted. Thank you!!" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity notfentity = new StringEntity(data);
       notification.setEntity(notfentity);

       // Send the request and retrieve the response
       HttpResponse notfresponse = notfhttpClient.execute(notification);

       // Handle the response
       int notfstatusCode = notfresponse.getStatusLine().getStatusCode();
       String notfresponseString = EntityUtils.toString(notfresponse.getEntity());

       // Update the booking status to "accepted"
       booking.setStatus("accepted");
       bookingRepository.save(booking);
       return "Booking accepted";
   }

   @Override
   @Transactional
   public  String rejectBookingRequest(Integer bookingId) throws IOException {
       Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
       Optional<User> customer = userRepository.findById(booking.getCustomer().getId());
       if (!booking.getStatus().equals("pending")) {
           throw new IllegalStateException("The booking is not in pending status");
       }

       HttpClient httpClient = HttpClientBuilder.create().build();
       HttpPost httpRequest = new HttpPost("https://exp.host/--/api/v2/push/send");

       httpRequest.setHeader("Content-Type", "application/json");

       String payload = "{"
               + "\"to\": \"" + customer.get().getDeviceToken() + "\","
               + "\"title\": \"" + "NepHench" + "\","
               + "\"body\": \"" + "Dear " + customer.get().getUsername()+ ", Sorry your booking request has been rejected due to some inconvenience . Thank you!!" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity entity = new StringEntity(payload);
       httpRequest.setEntity(entity);
       // Send the request and retrieve the response
       HttpResponse response = httpClient.execute(httpRequest);

       //For storing the notifications of each user
       HttpClient notfhttpClient = HttpClientBuilder.create().build();
       HttpPost notification = new HttpPost("http://localhost:9096/api/notifications");

       notification.setHeader("Content-Type", "application/json");
       String data = "{"
               + "\"user\": \"" + customer.get().getId() + "\","
               + "\"content\": \"" + "Dear " + customer.get().getUsername()+ ", Sorry your booking request has been rejected due to some inconvenience . Thank you!!" + "\""
               + "}";

       // Set the payload as the request body
       StringEntity notfentity = new StringEntity(data);
       notification.setEntity(notfentity);

       // Send the request and retrieve the response
       HttpResponse notfresponse = notfhttpClient.execute(notification);

       // Handle the response
       int notfstatusCode = notfresponse.getStatusLine().getStatusCode();
       String notfresponseString = EntityUtils.toString(notfresponse.getEntity());


       // Update the booking status to "accepted"
       booking.setStatus("rejected");
       bookingRepository.save(booking);
       return "Booking rejected";
   }

    @Override
    public List<Booking> getBookingsByServiceProviderId(Integer serviceProvider) {
        return bookingRepository.findByServiceProviderId(serviceProvider);
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Integer customer) {
        return bookingRepository.findByCustomerId(customer);
    }

    @Override
    public List<BookingsService> getServicesByBookingId(Integer booking) {
        return bookingsServicesRepository.findByBookingId(booking);
    }

    @Override
    public void taskCompleted(Integer bookingId) throws IOException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        Optional<User> customer = userRepository.findById(booking.getCustomer().getId());

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpRequest = new HttpPost("https://exp.host/--/api/v2/push/send");

        httpRequest.setHeader("Content-Type", "application/json");

        String payload = "{"
                + "\"to\": \"" + customer.get().getDeviceToken() + "\","
                + "\"title\": \"" + "NepHench" + "\","
                + "\"body\": \"" + "Dear " + customer.get().getUsername()+ ", Your requested service is completed.Please proceed further for the payment process , Thank you!!" + "\""
                + "}";

        // Set the payload as the request body
        StringEntity entity = new StringEntity(payload);
        httpRequest.setEntity(entity);
        // Send the request and retrieve the response
        HttpResponse response = httpClient.execute(httpRequest);

        // Handle the response
        int statusCode = response.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(response.getEntity());
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

}
