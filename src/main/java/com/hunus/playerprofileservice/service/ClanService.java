package com.hunus.playerprofileservice.service;

import com.hunus.playerprofileservice.dto.ClanDTO;

import java.util.Optional;
import java.util.UUID;

public interface ClanService {
    Optional<UUID> saveClan(ClanDTO clanDTO);

    Optional<ClanDTO> findClanById(UUID clanId);

    Optional<ClanDTO> findClanByIdOrName(UUID clanId, String clanName);
}
