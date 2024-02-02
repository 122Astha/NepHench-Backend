package com.example.NepHench.service;

import com.example.NepHench.beans.SiteConfigRequest;
import com.example.NepHench.model.SiteConfig;
import org.springframework.stereotype.Service;

@Service
public interface SiteConfigService {
    SiteConfig siteconfig(SiteConfigRequest siteConfigRequest);
}
