package com.example.NepHench.controller;

import com.example.NepHench.model.Role;
import com.example.NepHench.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
