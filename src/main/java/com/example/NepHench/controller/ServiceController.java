package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.ServiceRequest;
import com.example.NepHench.model.Nephenchservice;
import com.example.NepHench.repository.ServiceRepository;
import com.example.NepHench.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/services")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    private final ServiceService serviceService;

    public ServiceController(ServiceRepository serviceRepository, ServiceService serviceService) {
        this.serviceRepository = serviceRepository;
        this.serviceService = serviceService;
    }

    @GetMapping("")
    @ResponseBody
    public List<Nephenchservice> getAllServices() {
        return serviceRepository.findAll();
    }

    //Getting services through id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Nephenchservice>> getServiceById(@PathVariable(value = "id") Long service_id) {
        Optional<Nephenchservice> service = serviceRepository.findById(Math.toIntExact(service_id));
        return ResponseEntity.ok().body(service);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postService(@RequestBody ServiceRequest serviceRequest) {
        Nephenchservice service = serviceService.postService(serviceRequest);
        ApiResponse response = new ApiResponse("Service created successfully");
        return ResponseEntity.ok(response);
    }

}
