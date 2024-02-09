package com.hunus.playerprofileservice;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunus.playerprofileservice.dto.campaign.*;
import lombok.SneakyThrows;

import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.dto.DeviceDTO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

public final class PlayerProfileUtils {

    private static final ObjectMapper objectMapper = getObjectMapper();

    private PlayerProfileUtils() {
    }

    public static CampaignDTO getCurrentCampaign() {
        return CampaignDTO.builder()
            .game("mygame")
            .name("mycampaign")
            .priority(10.5)
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now())
            .enabled(true)
            .lastUpdated(LocalDateTime.now())
            .build();
    }

    public static PlayerProfileDTO generatePlayerProfile() {
        return generatePlayerProfile(null);
    }

    public static PlayerProfileDTO generatePlayerProfile(UUID playerId) {
        ClanDTO clan = new ClanDTO("Hello world clan");
        DeviceDTO device = new DeviceDTO("apple iphone 11", "vodafone", "123");
        Map<String, Integer> inventory = Map.of(
            "cash", 123,
            "coins", 123,
            "item_1", 1,
            "item_34", 3,
            "item_55", 2
        );
        return PlayerProfileDTO.builder()
            .playerId(playerId)
            .credential("apple_credential")
            .totalSpent(400)
            .totalRefund(0)
            .totalTransactions(5)
            .activeCampaigns(new ArrayList<>())
            .level(3)
            .experience(1000)
            .totalPlaytime(144)
            .country("CA")
            .language("fr")
            .gender("male")
            .birthdate(LocalDateTime.now())
            .inventory(inventory)
            .customField("mycustom")
            .clan(clan)
            .devices(List.of(device))
            .build();
    }

    public static CampaignDTO generateActiveCampaign(String name, List<String> hasCountries, List<String> hasItems,
            List<String> doesNotHaveCountries, List<String> doesNotHaveItems) {
        return CampaignDTO.builder()
            .game("mygame")
            .name(name)
            .priority(10.5)
            .matchers(MatchersDTO.builder()
                .level(LevelDTO.builder().min(1).max(3).build())
                .has(HasClauseDTO.builder()
                    .country(hasCountries)
                    .items(hasItems)
                    .build()
                ).doesNotHave(DoesNotHaveClauseDTO.builder()
                    .country(doesNotHaveCountries)
                    .items(doesNotHaveItems)
                    .build()
                ).build()
            )
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now())
            .lastUpdated(LocalDateTime.now())
            .enabled(true)
            .build();
    }

    @SneakyThrows
    public static String jsonBody(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
            .findAndRegisterModules()
            .setDateFormat(new SimpleDateFormat("yyyy-MM-ddhh:mm:ss"));
    }
}
