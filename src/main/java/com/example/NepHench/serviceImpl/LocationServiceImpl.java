package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.LocationRequest;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.Location;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.LocationRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Location location(LocationRequest location) {
        Optional<User> user = userRepository.findById(location.getUser());
        Optional<Location> existingLocation = locationRepository.findByUserId(location.getUser());
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        if (existingLocation.isPresent()) {
            Location currentLocation = existingLocation.get();
            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());
            return locationRepository.save(currentLocation);
        }else {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Location locations = new Location();
            locations.setUser(user.get());
            locations.setLatitude(location.getLatitude());
            locations.setLongitude(location.getLongitude());

            return locationRepository.save(locations);
        }
    }

    @Override
    public Location getLocationById(Integer user) {
        return locationRepository.findByUserId(user).get();
    }


}
