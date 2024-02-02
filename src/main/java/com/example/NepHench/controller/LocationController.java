package com.example.NepHench.controller;

import com.example.NepHench.beans.LocationRequest;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.Location;
import com.example.NepHench.repository.LocationRepository;
import com.example.NepHench.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    private final LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationService locationService,
                              LocationRepository locationRepository) {
        this.locationService = locationService;
        this.locationRepository = locationRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();

        if (!locations.isEmpty()) {
            return ResponseEntity.ok(locations);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //GET USER BY ID
    @GetMapping("/{user}")
    @ResponseBody
    public ResponseEntity<Location> getLocationByUserId(@PathVariable("user") Integer user) {
        Optional<Location> location = locationRepository.findByUserId(user);
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> location(@RequestBody LocationRequest locationRequest) {
        try {
            Location location = locationService.location(locationRequest);
            return ResponseEntity.ok().body(location);
        }
        catch (NotFoundException e){
            String errorMessage = "User not found";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", errorMessage));
        }
        }
    }

