package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.SiteConfigRequest;
import com.example.NepHench.model.SiteConfig;
import com.example.NepHench.repository.SiteConfigRepository;
import com.example.NepHench.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/siteconfig")
public class SiteConfigController {
    private final SiteConfigService siteConfigService;
    @Autowired
    private SiteConfigRepository siteConfigRepository;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    @GetMapping("")
    @ResponseBody
    public List<SiteConfig> getAllServices() {
        return siteConfigRepository.findAll();
    }

    //Getting services through id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<SiteConfig>> getSiteConfigById(@PathVariable(value = "id") Integer id) {
        Optional<SiteConfig> siteConfig = siteConfigRepository.findById(Math.toIntExact(id));
        return ResponseEntity.ok().body(siteConfig);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> siteconfig(@RequestBody SiteConfigRequest siteConfigRequest) {
        SiteConfig siteConfig = siteConfigService.siteconfig(siteConfigRequest);
        ApiResponse response = new ApiResponse("Site Config saved successfully");
        return ResponseEntity.ok(response);
    }
}
