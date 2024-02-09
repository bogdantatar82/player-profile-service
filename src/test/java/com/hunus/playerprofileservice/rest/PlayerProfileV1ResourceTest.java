package com.hunus.playerprofileservice.rest;

import static com.hunus.playerprofileservice.PlayerProfileUtils.generateActiveCampaign;
import static com.hunus.playerprofileservice.PlayerProfileUtils.generatePlayerProfile;
import static com.hunus.playerprofileservice.PlayerProfileUtils.jsonBody;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;
import com.hunus.playerprofileservice.service.PlayerProfileMatcherService;

@WebMvcTest(PlayerProfileV1Resource.class)
@WebAppConfiguration
public class PlayerProfileV1ResourceTest {
    private static final String ENDPOINT = "/v1/profiles";

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private PlayerProfileMatcherService playerProfileMatcherService;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
    void matchPlayerProfileV1_returnsOk() throws Exception {
        UUID playerId = UUID.randomUUID();
        List<CampaignDTO> activeCampaigns = getActiveCampaigns();
        PlayerProfileDTO playerProfile = generatePlayerProfile();
        when(playerProfileMatcherService.matchPlayerProfile(playerId, activeCampaigns)).thenReturn(Optional.of(playerProfile));

        mvc.perform(
                post(String.format(ENDPOINT + "/%s/match", playerId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody(activeCampaigns))
            ).andExpect(status().isOk())
            .andExpect(content().json(jsonBody(playerProfile)));
    }

    @Test
    void matchPlayerProfile_returnsNotFound() throws Exception {
        UUID playerId = UUID.randomUUID();
        List<CampaignDTO> activeCampaigns = getActiveCampaigns();
        when(playerProfileMatcherService.matchPlayerProfile(playerId, activeCampaigns)).thenReturn(Optional.empty());

        mvc.perform(
            post(String.format(ENDPOINT + "/%s/match", playerId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody(activeCampaigns))
        ).andExpect(status().isNotFound());
    }

    private static List<CampaignDTO> getActiveCampaigns() {
        return List.of(
            generateActiveCampaign("mylastcampaign",
                List.of("UK", "RO", "CA"),
                List.of("item_1"),
                null,
                List.of("item_4")
            ),
            generateActiveCampaign("mycampaign",
                List.of("UK", "RO", "CA"),
                List.of("item_1"),
                null,
                List.of("item_3")
            )
        );
    }
}
