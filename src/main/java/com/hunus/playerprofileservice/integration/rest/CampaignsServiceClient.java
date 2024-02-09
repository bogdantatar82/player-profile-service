package com.hunus.playerprofileservice.integration.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignListDTO;

@Service
public class CampaignsServiceClient {
    protected static final Logger logger = LoggerFactory.getLogger(CampaignsServiceClient.class);

    private String campaignsServiceUrl;
    private RestTemplate restTemplate;

    @Autowired
    public CampaignsServiceClient(@Value("${hosts.campaignsservice}") String campaignsServiceUrl,
            @Qualifier("campaignsServiceRestTemplate") RestTemplate restTemplate) {
        this.campaignsServiceUrl = campaignsServiceUrl;
        this.restTemplate = restTemplate;
    }

    public List<CampaignDTO> getCurrentActiveCampaigns(int page, int limit) {
        String url = campaignsServiceUrl + "/campaigns?limit={limit}&page={page}";
        Map<String, Object> params = Map.of(
            "page", page,
            "limit", limit
        );
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, CampaignListDTO.class, params))
                .map(CampaignListDTO::getCampaigns)
                .orElseGet(ArrayList::new);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.warn("Current campaigns not found");
                return List.of();
            }
            throw new IllegalStateException("Error while fetching current campaigns");
        }
    }
}
