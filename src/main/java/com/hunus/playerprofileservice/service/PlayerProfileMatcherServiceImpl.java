package com.hunus.playerprofileservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.dto.campaign.MatchersDTO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.integration.rest.CampaignsServiceClient;

@Service
public class PlayerProfileMatcherServiceImpl implements PlayerProfileMatcherService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileMatcherServiceImpl.class);
    private final PlayerProfileService playerProfileService;
    private final CampaignsServiceClient campaignsServiceClient;

    @Autowired
    public PlayerProfileMatcherServiceImpl(PlayerProfileService playerProfileService,
            CampaignsServiceClient campaignsServiceClient) {
        this.playerProfileService = playerProfileService;
        this.campaignsServiceClient = campaignsServiceClient;
    }

    @Override
    public Optional<PlayerProfileDTO> matchPlayerProfile(UUID playerId) {
        return playerProfileService.findPlayerProfile(playerId)
            .flatMap(playerProfile -> updatePlayerProfileIfNeeded(playerProfile, null));
    }

    @Override
    public Optional<PlayerProfileDTO> matchPlayerProfile(UUID playerId, List<CampaignDTO> campaigns) {
        return playerProfileService.findPlayerProfile(playerId)
            .flatMap(playerProfile -> updatePlayerProfileIfNeeded(playerProfile, campaigns));

    }

    /**
     * Match the player profile with the current active campaigns.
     * In case they match, player profile is updated using campaigns names.
     */
    private Optional<PlayerProfileDTO> updatePlayerProfileIfNeeded(PlayerProfileDTO playerProfile, List<CampaignDTO> campaigns) {
        try {
            List<String> campaignNames = getActiveCampaignNames(playerProfile, campaigns);
            if (campaignNames.isEmpty()) {
                return Optional.of(playerProfile);
            }
            return playerProfileService.updatePlayerProfileWithCampaignNames(
                playerProfile.getPlayerId(),
                campaignNames
            );
        } catch (Exception ex) {
            logger.error("Error occurred when updating profile for player id:" + playerProfile.getPlayerId(), ex);
            return Optional.of(playerProfile);
        }
    }

    private List<CampaignDTO> getCurrentActiveCampaigns() {
        return campaignsServiceClient.getCurrentActiveCampaigns(0, 10);
    }

    private List<String> getActiveCampaignNames(PlayerProfileDTO playerProfile, List<CampaignDTO> campaigns) {
        return Optional.ofNullable(campaigns).orElseGet(this::getCurrentActiveCampaigns).stream()
            .filter(campaign -> isPlayerProfileMatchingCampaign(playerProfile, campaign))
            .map(CampaignDTO::getName)
            .collect(Collectors.toList());
    }

    /**
     * Determine if the current player profile matches ANY of the campaign conditions (matchers)
     */
    private boolean isPlayerProfileMatchingCampaign(PlayerProfileDTO playerProfile, CampaignDTO currentCampaign) {
        return Optional.ofNullable(currentCampaign)
            .map(CampaignDTO::getMatchers)
            .map(matchers -> anyCampaignConditionsMatch(playerProfile, matchers))
            .orElse(Boolean.FALSE);
    }

    private static boolean anyCampaignConditionsMatch(PlayerProfileDTO playerProfile, MatchersDTO matchers) {
        return matchers.findMatchers().stream()
            .map(m -> m.test(playerProfile))
            .reduce(Boolean.FALSE, (b1, b2) -> b1 || b2);
    }
}
