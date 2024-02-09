package com.hunus.playerprofileservice.service;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

public interface PlayerProfileService {

    Optional<PlayerProfileDTO> findPlayerProfile(UUID playerId);

    Optional<UUID> savePlayerProfile(PlayerProfileDTO playerProfileDTO);

    Optional<PlayerProfileDTO> updatePlayerProfileWithCampaignNames(UUID playerId, List<String> campaignNames);

    Page<PlayerProfileDTO> searchPlayerProfiles(SearchParams searchParams);

    @Data
    @AllArgsConstructor
    @Builder
    final class SearchParams {
        private final String customField;
        private final LocalDate lastSessionBefore;
        private final LocalDate lastSessionAfter;
        private final Integer page;
        private final Integer limit;
        private final String ordering;

        public PageRequest toPageRequest() {
            return PageRequest.of(page, limit, ASC, ordering);
        }
    }
}
