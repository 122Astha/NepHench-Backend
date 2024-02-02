package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.ServiceRequest;
import com.example.NepHench.model.Nephenchservice;
import com.example.NepHench.repository.ServiceRepository;
import com.example.NepHench.service.ServiceService;
import org.springframework.stereotype.Service;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Nephenchservice postService(ServiceRequest service) {
        Nephenchservice services = new Nephenchservice();
        services.setName(service.getName());
        services.setDescription(service.getDescription());
        services.setPrice(service.getPrice());
        services.setRole(service.getRole());
        services.setImage(service.getImage());

        return serviceRepository.save(services);
    }
}
