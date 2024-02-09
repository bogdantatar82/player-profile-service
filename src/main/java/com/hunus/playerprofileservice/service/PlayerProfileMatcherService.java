package com.hunus.playerprofileservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;

public interface PlayerProfileMatcherService {

    Optional<PlayerProfileDTO> matchPlayerProfile(UUID playerId);

    Optional<PlayerProfileDTO> matchPlayerProfile(UUID playerId, List<CampaignDTO> campaigns);
}
