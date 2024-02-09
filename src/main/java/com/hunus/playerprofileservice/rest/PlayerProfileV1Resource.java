package com.hunus.playerprofileservice.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.service.PlayerProfileMatcherService;

@RestController
@RequestMapping("/v1/profiles")
public class PlayerProfileV1Resource {
    private final PlayerProfileMatcherService playerProfileMatcherService;

    @Autowired
    public PlayerProfileV1Resource(PlayerProfileMatcherService playerProfileMatcherService) {
        this.playerProfileMatcherService = playerProfileMatcherService;
    }

    /**
     * Matches the player profile with the received active campaigns.
     * In case any of them match with the player profile, the player profile
     * is updated using the names of these active campaigns.
     * @param playerId - the id of the player profile
     * @param campaigns - the list with the active campaigns
     * @return player profile updated with the active campaigns names
     */
    @PostMapping(value = "/{playerId}/match", produces="application/json")
    public ResponseEntity<PlayerProfileDTO> matchPlayerProfile(@PathVariable UUID playerId,
        @RequestBody List<CampaignDTO> campaigns) {
        return playerProfileMatcherService.matchPlayerProfile(playerId, campaigns)
            .map(ResourceUtils::ok)
            .orElseGet(ResourceUtils::notFound);
    }
}
