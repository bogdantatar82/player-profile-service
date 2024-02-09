package com.hunus.playerprofileservice.integration.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Before;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignListDTO;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CampaignsServiceClientTest {
    private final String campaignsServiceUrl = "http://localhost:8080";
    @Mock
    private RestTemplate restTemplate;

    private CampaignsServiceClient service;

    @Before
    public void setup() {
        service = new CampaignsServiceClient(campaignsServiceUrl, restTemplate);
    }

    @Test
    public void should_return_empty_when_campaigns_not_found() {
        // given
        UUID playerId = UUID.randomUUID();
        when(restTemplate.getForObject(any(URI.class), eq(CampaignDTO.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // when
        List<CampaignDTO> campaigns = service.getCurrentActiveCampaigns(0, 10);

        // then
        assertTrue(campaigns.isEmpty());
    }

    @Test
    public void should_return_campaign_when_campaigns_service_responds_with_OK() {
        // given
        CampaignDTO campaign = new CampaignDTO();
        CampaignListDTO campaignList = new CampaignListDTO(List.of(campaign));
        when(restTemplate.getForObject(anyString(), eq(CampaignListDTO.class), anyMap()))
            .thenReturn(campaignList);

        // when
        List<CampaignDTO> campaigns = service.getCurrentActiveCampaigns(0, 10);

        // then
        assertFalse(campaigns.isEmpty());
        assertEquals(campaign, campaigns.get(0));
    }

    @Test
    public void should_throw_exception_when_campaigns_service_responds_with_error() {
        // given
        when(restTemplate.getForObject(anyString(), eq(CampaignListDTO.class), anyMap()))
            .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        try {
            // when
            service.getCurrentActiveCampaigns(0, 10);
        } catch (IllegalStateException ex) {
            // then
            assertTrue(ex.getMessage().contains("Error while fetching current campaigns"));
        }
    }
}
