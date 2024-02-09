package com.hunus.playerprofileservice.rest;

import static com.hunus.playerprofileservice.PlayerProfileUtils.jsonBody;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.service.ClanService;

@WebMvcTest(ClanResource.class)
@WebAppConfiguration
public class ClanResourceTest {
    private static final String ENDPOINT = "/v0/clans";

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private ClanService clanService;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
    void addClan_returnsInternalServerError() throws Exception {
        ClanDTO clan = new ClanDTO("Great clan");

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(clan))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError());
    }

    @Test
    void addClan_returnsBadRequest() throws Exception {
        ClanDTO clan = new ClanDTO("");

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(clan))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addClan_returnsCreated() throws Exception {
        UUID clanId = UUID.randomUUID();
        ClanDTO clan = new ClanDTO("Great clan");
        when(clanService.saveClan(any(ClanDTO.class))).thenReturn(Optional.of(clanId));

        mvc.perform(
            post(ENDPOINT)
                .content(jsonBody(clan))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.clanId").value(clanId.toString()));
    }
}
