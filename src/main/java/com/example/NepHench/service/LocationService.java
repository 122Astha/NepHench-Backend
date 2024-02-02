package com.example.NepHench.service;

import com.example.NepHench.beans.LocationRequest;
import com.example.NepHench.model.Location;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    Location location(LocationRequest locationRequest);

    Object getLocationById(Integer userId);
}
