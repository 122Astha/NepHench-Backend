package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.SiteConfigRequest;
import com.example.NepHench.model.SiteConfig;
import com.example.NepHench.repository.SiteConfigRepository;
import com.example.NepHench.service.SiteConfigService;
import org.springframework.stereotype.Service;

@Service
public class SiteConfigServiceImpl implements SiteConfigService {
    private final SiteConfigRepository siteConfigRepository;

    public SiteConfigServiceImpl(SiteConfigRepository siteConfigRepository) {
        this.siteConfigRepository = siteConfigRepository;
    }

    @Override
    public SiteConfig siteconfig(SiteConfigRequest siteConfigRequest) {
        String sitekey = siteConfigRequest.getSiteKey();
        String sitevalue=  siteConfigRequest.getSiteValue();
        byte[] image = siteConfigRequest.getImage();
        SiteConfig siteConfig = new SiteConfig();
        siteConfig.setSiteKey(sitekey);
        siteConfig.setSiteValue(sitevalue);
        siteConfig.setImage(image);

        return siteConfigRepository.save(siteConfig);
    }
}
