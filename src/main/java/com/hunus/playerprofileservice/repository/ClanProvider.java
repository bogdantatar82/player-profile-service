package com.hunus.playerprofileservice.repository;

import com.hunus.playerprofileservice.database.jpa.Clan;
import com.hunus.playerprofileservice.dto.ClanDTO;

import java.util.Optional;
import java.util.UUID;

public interface ClanProvider {

    Optional<ClanDTO> findById(UUID clanId);
    Optional<Clan> findByName(String clanName);
}
