package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.ServiceRequest;
import com.example.NepHench.beans.SpRoleRequest;
import com.example.NepHench.model.Nephenchservice;
import com.example.NepHench.model.Serviceproviderrole;
import com.example.NepHench.service.ServiceProviderRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serviceprovider")
public class ServiceProviderController {
    @Autowired
    private ServiceProviderRoleService service;

    @GetMapping("")
    @ResponseBody
    public List<Serviceproviderrole> getAllRoles() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Serviceproviderrole getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @PostMapping("/role")
    public ResponseEntity<ApiResponse> postServiceProviderRole(@RequestBody SpRoleRequest spRoleRequest) {
        Serviceproviderrole roles = service.postServiceProviderRole(spRoleRequest);
        ApiResponse response = new ApiResponse("Role created successfully");
        return ResponseEntity.ok(response);
    }

}
