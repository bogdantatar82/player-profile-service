package com.hunus.playerprofileservice.rest;

import static com.hunus.playerprofileservice.PlayerProfileUtils.generatePlayerProfile;
import static com.hunus.playerprofileservice.PlayerProfileUtils.jsonBody;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.hunus.playerprofileservice.service.PlayerProfileMatcherService;
import com.hunus.playerprofileservice.service.PlayerProfileService;

@WebMvcTest(PlayerProfileResource.class)
@WebAppConfiguration
public class PlayerProfileResourceTest {
    private static final String ENDPOINT = "/v0/profiles";

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private PlayerProfileService playerProfileService;
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
    void addPlayerProfile_returnsInternalServerError() throws Exception {
        PlayerProfileDTO playerProfile = generatePlayerProfile();

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(playerProfile))
                .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isInternalServerError());
    }

    @Test
    void addPlayerProfile_returnsBadRequest() throws Exception {
        PlayerProfileDTO playerProfile = generatePlayerProfile().toBuilder().credential(null).build();

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(playerProfile))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addPlayerProfile_returnsCreated() throws Exception {
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile();
        when(playerProfileService.savePlayerProfile(any(PlayerProfileDTO.class))).thenReturn(Optional.of(playerId));

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(playerProfile))
                .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.playerId").value(playerId.toString()));
    }

    @Test
    void matchPlayerProfile_returnsOk() throws Exception {
        UUID playerId = UUID.randomUUID();
        PlayerProfileDTO playerProfile = generatePlayerProfile();
        when(playerProfileMatcherService.matchPlayerProfile(playerId)).thenReturn(Optional.of(playerProfile));

        mvc.perform(
            post(String.format(ENDPOINT + "/%s/match", playerId))
                .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(content().json(jsonBody(playerProfile)));
    }

    @Test
    void matchPlayerProfile_returnsNotFound() throws Exception {
        UUID playerId = UUID.randomUUID();
        when(playerProfileMatcherService.matchPlayerProfile(playerId)).thenReturn(Optional.empty());

        mvc.perform(
                post(String.format(ENDPOINT + "/%s/match", playerId))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());
    }

    @Test
    void searchPlayerProfiles_returnsBadRequest() throws Exception {
        String after = "2024-01-21", before = "2024-01-20";
        String url = ENDPOINT + String.format("?lastSessionAfter=%s&lastSessionBefore=%s", after, before);
        mvc.perform(
            get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void searchPlayerProfiles_returnsOk() throws Exception {
        String after = "2024-02-21", before = "2024-02-22";
        String url = ENDPOINT + String.format("?lastSessionAfter=%s&lastSessionBefore=%s", after, before);
        mvc.perform(
            get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
