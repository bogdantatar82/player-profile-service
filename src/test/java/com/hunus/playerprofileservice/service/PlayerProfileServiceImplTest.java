package com.hunus.playerprofileservice.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.hunus.playerprofileservice.dto.ClanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hunus.playerprofileservice.database.jpa.Clan;
import com.hunus.playerprofileservice.repository.jpa.PlayerProfileDAO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.database.jpa.PlayerProfile;
import com.hunus.playerprofileservice.service.PlayerProfileService.SearchParams;

@ExtendWith(MockitoExtension.class)
public class PlayerProfileServiceImplTest {
    @Mock
    private ClanService clanService;
    @Mock
    private PlayerProfileDAO playerProfileDAO;
    @Captor
    private ArgumentCaptor<PlayerProfile> playerProfileArgumentCaptor;
    private PlayerProfileService service;

    @BeforeEach
    public void setup() {
        service = new PlayerProfileServiceImpl(clanService, playerProfileDAO);
    }

    @Test
    void savePlayerProfile_returnsEmptyWhenNullInput() {
        assertTrue(service.savePlayerProfile(null).isEmpty());
    }

    @Test
    void savePlayerProfile_returnsSavedPlayerId() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfileDTO = mock(PlayerProfileDTO.class);
        ClanDTO clan = mock(ClanDTO.class);
        PlayerProfile playerProfile = mock(PlayerProfile.class);
        when(playerProfile.getPlayerId()).thenReturn(playerId);
        when(clanService.findClanByIdOrName(any(UUID.class), anyString())).thenReturn(Optional.of(clan));
        when(playerProfileDAO.save(any(PlayerProfile.class))).thenReturn(playerProfile);

        // when
        Optional<UUID> savedPlayerId = service.savePlayerProfile(playerProfileDTO);

        // then
        assertTrue(savedPlayerId.isPresent());
        assertEquals(playerId, savedPlayerId.get());
    }

    @Test
    void savePlayerProfile_returnsNoPlayerIdWhenExceptionIsThrown() {
        // given
        PlayerProfileDTO playerProfileDTO = mock(PlayerProfileDTO.class);

        // when
        Optional<UUID> savedPlayerId = service.savePlayerProfile(playerProfileDTO);

        // then
        assertTrue(savedPlayerId.isEmpty());
    }

    @Test
    void updatePlayerProfileWithCampaign_returnsUpdatedPlayerProfile() {
        // given
        UUID playerId = UUID.randomUUID();
        String campaign = "best campaign";
        Optional<PlayerProfile> playerProfile = Optional.of(new PlayerProfile());
        when(playerProfileDAO.findById(playerId)).thenReturn(playerProfile);

        // when
        service.updatePlayerProfileWithCampaignNames(playerId, List.of(campaign));

        // then
        Mockito.verify(playerProfileDAO).save(playerProfileArgumentCaptor.capture());
        assertFalse(playerProfileArgumentCaptor.getValue().getActiveCampaigns().isEmpty());
        assertEquals(campaign, playerProfileArgumentCaptor.getValue().getActiveCampaigns().get(0));
    }

    @Test
    void searchPlayerProfiles_searchesPlayerProfilesByCustomField() {
        // given
        UUID playerId = UUID.randomUUID();
        String customField = "mycustom";
        LocalDate beforeDate = LocalDate.now();
        LocalDate afterDate = beforeDate.minusMonths(1);
        int page = 1, limit = 10;
        String ordering = "modified";
        PageRequest pageRequest = PageRequest.of(page, limit, ASC, ordering);
        List<PlayerProfile> playerProfiles = List.of(PlayerProfile.builder().playerId(playerId).build());
        Page<PlayerProfile> pageResult = new PageImpl<>(playerProfiles);
        SearchParams searchParams = SearchParams.builder()
            .customField(customField)
            .lastSessionAfter(afterDate)
            .lastSessionBefore(beforeDate)
            .page(page)
            .limit(limit)
            .ordering(ordering)
            .build();
        ArgumentCaptor<String> customFieldCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<LocalDate> afterDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> beforeDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<Pageable> pageCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(playerProfileDAO.findByCustomFieldAndLastSession(customField, afterDate, beforeDate, pageRequest))
            .thenReturn(pageResult);

        // when
        Page<PlayerProfileDTO> searchResult = service.searchPlayerProfiles(searchParams);

        // then
        Mockito.verify(playerProfileDAO).findByCustomFieldAndLastSession(customFieldCaptor.capture(), afterDateCaptor.capture(), beforeDateCaptor.capture(), pageCaptor.capture());
        assertEquals(customField, customFieldCaptor.getValue());
        assertEquals(afterDate, afterDateCaptor.getValue());
        assertEquals(beforeDate, beforeDateCaptor.getValue());
        assertEquals(page, pageCaptor.getValue().getPageNumber());
        assertEquals(limit, pageCaptor.getValue().getPageSize());
        assertEquals(ordering, pageCaptor.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(pageResult.getSize(), searchResult.getSize());
        assertEquals(pageResult.getContent().get(0).getPlayerId(), searchResult.getContent().get(0).getPlayerId());
    }

    @Test
    void searchPlayerProfiles_searchesPlayerProfilesByLastSession() {
        // given
        UUID playerId = UUID.randomUUID();
        LocalDate beforeDate = LocalDate.now();
        LocalDate afterDate = beforeDate.minusMonths(1);
        int page = 1, limit = 10;
        String ordering = "modified";
        PageRequest pageRequest = PageRequest.of(page, limit, ASC, ordering);
        List<PlayerProfile> playerProfiles = List.of(PlayerProfile.builder().playerId(playerId).build());
        Page<PlayerProfile> pageResult = new PageImpl<>(playerProfiles);
        SearchParams searchParams = SearchParams.builder()
            .lastSessionAfter(afterDate)
            .lastSessionBefore(beforeDate)
            .page(page)
            .limit(limit)
            .ordering(ordering)
            .build();
        ArgumentCaptor<LocalDate> afterDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> beforeDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<Pageable> pageCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(playerProfileDAO.findByLastSession(afterDate, beforeDate, pageRequest))
            .thenReturn(pageResult);

        // when
        Page<PlayerProfileDTO> searchResult = service.searchPlayerProfiles(searchParams);

        // then
        Mockito.verify(playerProfileDAO).findByLastSession(afterDateCaptor.capture(), beforeDateCaptor.capture(), pageCaptor.capture());
        assertEquals(afterDate, afterDateCaptor.getValue());
        assertEquals(beforeDate, beforeDateCaptor.getValue());
        assertEquals(page, pageCaptor.getValue().getPageNumber());
        assertEquals(limit, pageCaptor.getValue().getPageSize());
        assertEquals(ordering, pageCaptor.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(pageResult.getSize(), searchResult.getSize());
        assertEquals(pageResult.getContent().get(0).getPlayerId(), searchResult.getContent().get(0).getPlayerId());
    }
}
