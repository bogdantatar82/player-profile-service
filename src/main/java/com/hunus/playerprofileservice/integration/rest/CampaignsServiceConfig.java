package com.hunus.playerprofileservice.integration.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CampaignsServiceConfig {

    @Bean(name = "campaignsServiceRestTemplate")
    public RestTemplate playerCampaignRestTemplate() {
        return new RestTemplate();
    }
}
