package com.hunus.playerprofileservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.hunus.playerprofileservice.dto.ClanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.hunus.playerprofileservice.repository.jpa.PlayerProfileDAO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.database.jpa.Clan;
import com.hunus.playerprofileservice.database.jpa.Device;
import com.hunus.playerprofileservice.database.jpa.PlayerProfile;

@Service
public class PlayerProfileServiceImpl implements PlayerProfileService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileServiceImpl.class);

    private final ClanService clanService;
    private final PlayerProfileDAO playerProfileDAO;

    @Autowired
    public PlayerProfileServiceImpl(ClanService clanService, PlayerProfileDAO playerProfileDAO) {
        this.clanService = clanService;
        this.playerProfileDAO = playerProfileDAO;
    }

    @Override
    public Optional<PlayerProfileDTO> findPlayerProfile(UUID playerId) {
        try {
            return playerProfileDAO.findById(playerId)
                .map(PlayerProfile::toPlayerProfileDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while retrieving profile for player id:" + playerId, ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UUID> savePlayerProfile(PlayerProfileDTO playerProfileDTO) {
        try {
            return Optional.ofNullable(playerProfileDTO)
                .map(PlayerProfileDTO::getClan)
                .flatMap(clan -> clanService.findClanByIdOrName(clan.getId(), clan.getName()))
                .flatMap(clan -> toPlayerProfile(playerProfileDTO, clan))
                .map(playerProfileDAO::save)
                .map(PlayerProfile::getPlayerId);
        } catch (Exception ex) {
            logger.error("Error occurred when saving player profile:" + playerProfileDTO, ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<PlayerProfileDTO> updatePlayerProfileWithCampaignNames(UUID playerId, List<String> campaignNames) {
        return playerProfileDAO.findById(playerId)
            .map(playerProfile -> playerProfile.withActiveCampaigns(campaignNames))
            .map(playerProfileDAO::save)
            .map(PlayerProfile::toPlayerProfileDTO);
    }

    @Override
    public Page<PlayerProfileDTO> searchPlayerProfiles(SearchParams searchParams) {
        return Optional.ofNullable(searchParams.getCustomField())
            .map(customField -> playerProfileDAO.findByCustomFieldAndLastSession(
                customField,
                searchParams.getLastSessionAfter(),
                searchParams.getLastSessionBefore(),
                searchParams.toPageRequest()
            )).orElseGet(() -> playerProfileDAO.findByLastSession(
                searchParams.getLastSessionAfter(),
                searchParams.getLastSessionBefore(),
                searchParams.toPageRequest()
            )).map(PlayerProfile::toPlayerProfileDTO);
    }

    private static Optional<PlayerProfile> toPlayerProfile(PlayerProfileDTO input, ClanDTO clan) {
        return Optional.ofNullable(input)
            .map(playerProfile -> {
                List<Device> devices = Optional.ofNullable(input.getDevices())
                    .map(list -> list.stream().map(dto -> new Device(dto.getModel(), dto.getCarrier(), dto.getFirmware())).toList())
                    .orElse(List.of());
                return PlayerProfile.builder()
                    .playerId(input.getPlayerId())
                    .credential(input.getCredential())
                    .created(input.getCreated())
                    .modified(input.getModified())
                    .lastSession(Optional.ofNullable(input.getLastSession()).orElseGet(LocalDateTime::now))
                    .totalSpent(input.getTotalSpent())
                    .totalRefund(input.getTotalRefund())
                    .totalTransactions(input.getTotalTransactions())
                    .lastPurchase(input.getLastPurchase())
                    .activeCampaigns(input.getActiveCampaigns())
                    .level(input.getLevel())
                    .experience(input.getExperience())
                    .totalPlaytime(input.getTotalPlaytime())
                    .country(input.getCountry())
                    .language(input.getLanguage())
                    .birthdate(input.getBirthdate())
                    .gender(input.getGender())
                    .inventory(input.getInventory())
                    .customField(input.getCustomField())
                    .clan(new Clan(clan.getName()))
                    .devices(devices)
                    .build();
            });
    }
}
