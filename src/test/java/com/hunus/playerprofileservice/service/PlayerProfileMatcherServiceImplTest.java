package com.hunus.playerprofileservice.service;

import static com.hunus.playerprofileservice.PlayerProfileUtils.generatePlayerProfile;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hunus.playerprofileservice.PlayerProfileUtils;
import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.dto.campaign.DoesNotHaveClauseDTO;
import com.hunus.playerprofileservice.dto.campaign.HasClauseDTO;
import com.hunus.playerprofileservice.dto.campaign.LevelDTO;
import com.hunus.playerprofileservice.dto.campaign.MatchersDTO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.integration.rest.CampaignsServiceClient;

@ExtendWith(MockitoExtension.class)
class PlayerProfileMatcherServiceImplTest {
    @Mock
    private PlayerProfileService playerProfileService;
    @Mock
    private CampaignsServiceClient campaignsServiceClient;

    private PlayerProfileMatcherService service;

    @BeforeEach
    public void setup() {
        service = new PlayerProfileMatcherServiceImpl(playerProfileService, campaignsServiceClient);
    }

    @Test
    void matchPlayerProfile_addsToActiveCampaignBasedOnLevelMatch() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(1, 3))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName())))
            .thenReturn(Optional.of(playerProfile.toBuilder().activeCampaigns(List.of(currentCampaign.getName())).build()));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_addsToActiveCampaignBasedOnHasMatch() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .country("CA")
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(5, 8))
            .has(new HasClauseDTO(List.of("US","RO","CA"), List.of("item_1")))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName())))
            .thenReturn(Optional.of(playerProfile.toBuilder().activeCampaigns(List.of(currentCampaign.getName())).build()));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_addsToActiveCampaignBasedOnHasMatchWithCountryOnly() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .country("CA")
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(5, 8))
            .has(new HasClauseDTO(List.of("US","RO","CA"), List.of()))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName())))
            .thenReturn(Optional.of(playerProfile.toBuilder().activeCampaigns(List.of(currentCampaign.getName())).build()));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_addsToActiveCampaignBasedOnDoesNotHaveMatch() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .country("UK")
            .inventory(Map.of("item_2", 1))
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(5, 8))
            .has(new HasClauseDTO(List.of("US","RO","CA"), List.of("item_1")))
            .doesNotHave(new DoesNotHaveClauseDTO(null, List.of("item_4")))
            .build()
        );
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName())))
            .thenReturn(Optional.of(playerProfile.toBuilder().activeCampaigns(List.of(currentCampaign.getName())).build()));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_addsToActiveCampaignBasedOnDoesNotHaveMatchAndNoHasMatch() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .country("UK")
            .inventory(Map.of("item_2", 1))
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(5, 8))
            .has(null)
            .doesNotHave(new DoesNotHaveClauseDTO(null, List.of("item_4")))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName())))
            .thenReturn(Optional.of(playerProfile.toBuilder().activeCampaigns(List.of(currentCampaign.getName())).build()));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_doesNotAddToActiveCampaign() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId).toBuilder()
            .level(3)
            .country("UK")
            .inventory(Map.of("item_4", 1))
            .build();

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(new LevelDTO(5, 8))
            .has(new HasClauseDTO(List.of("US","RO","CA"), List.of("item_1")))
            .doesNotHave(new DoesNotHaveClauseDTO(null, List.of("item_4")))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertFalse(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_doesNotAddToActiveCampaignAsMatchersAreInvalid() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId);

        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        currentCampaign.setMatchers(MatchersDTO.builder()
            .level(null)
            .has(new HasClauseDTO(List.of(), null))
            .doesNotHave(new DoesNotHaveClauseDTO(null, List.of()))
            .build()
        );
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertFalse(matchedPlayerProfile.get().getActiveCampaigns().contains(currentCampaign.getName()));
    }

    @Test
    void matchPlayerProfile_performsNoUpdateOnPlayerProfileWhenNoCurrentCampaign() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId);

        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of());

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().isEmpty());
    }

    @Test
    void matchPlayerProfile_performsNoUpdateOnPlayerProfileWhenExceptionGettingCurrentCampaign() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId);
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenThrow(new RuntimeException());

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().isEmpty());
    }

    @Test
    void matchPlayerProfile_returnsNoPlayerProfileWhenExceptionGettingPlayerProfile() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile(playerId);
        CampaignDTO currentCampaign = PlayerProfileUtils.getCurrentCampaign();
        when(playerProfileService.findPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));
        when(campaignsServiceClient.getCurrentActiveCampaigns(0, 10)).thenReturn(List.of(currentCampaign));
        Mockito.lenient().when(playerProfileService.updatePlayerProfileWithCampaignNames(playerId, List.of(currentCampaign.getName()))).thenThrow(new RuntimeException());

        // when
        Optional<PlayerProfileDTO> matchedPlayerProfile = service.matchPlayerProfile(playerId);

        // then
        assertTrue(matchedPlayerProfile.isPresent());
        assertTrue(matchedPlayerProfile.get().getActiveCampaigns().isEmpty());
    }
}
