package com.example.NepHench.service;

import com.example.NepHench.beans.ServiceRequest;
import com.example.NepHench.model.Nephenchservice;

public interface ServiceService {
    Nephenchservice postService(ServiceRequest serviceRequest);

}
