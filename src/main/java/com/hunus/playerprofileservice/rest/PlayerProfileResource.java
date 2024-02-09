package com.hunus.playerprofileservice.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.dto.response.SavedPlayerProfileResponse;
import com.hunus.playerprofileservice.service.PlayerProfileMatcherService;
import com.hunus.playerprofileservice.service.PlayerProfileService;
import com.hunus.playerprofileservice.service.PlayerProfileService.SearchParams;

@RestController
@RequestMapping("/v0/profiles")
public class PlayerProfileResource {

    private final PlayerProfileService playerProfileService;
    private final PlayerProfileMatcherService playerProfileMatcherService;

    @Autowired
    public PlayerProfileResource(PlayerProfileService playerProfileService,
            PlayerProfileMatcherService playerProfileMatcherService) {
        this.playerProfileService = playerProfileService;
        this.playerProfileMatcherService = playerProfileMatcherService;
    }

    @GetMapping(value = "", produces="application/json", consumes="application/json")
    public ResponseEntity<Page<PlayerProfileDTO>> searchPlayerProfiles(
            @RequestParam(value = "customField", required = false) String customField,
            @RequestParam(value = "lastSessionAfter", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastSessionAfter,
            @RequestParam(value = "lastSessionBefore", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastSessionBefore,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @RequestParam(value = "ordering", required = false, defaultValue = "modified") String ordering) {
        LocalDate beforeDate = Optional.ofNullable(lastSessionBefore)
            .orElseGet(LocalDate::now);
        LocalDate afterDate = Optional.ofNullable(lastSessionAfter)
            .orElseGet(() -> beforeDate.minusMonths(1));
        if (isDateTimeIntervalInvalid(afterDate, beforeDate)) {
            return ResourceUtils.badRequest();
        }
        SearchParams searchParams = SearchParams.builder()
            .customField(customField)
            .lastSessionAfter(afterDate)
            .lastSessionBefore(beforeDate)
            .page(page)
            .limit(limit)
            .ordering(ordering)
            .build();
        return ResourceUtils.ok(playerProfileService.searchPlayerProfiles(searchParams));
    }

    @GetMapping(value = "/{playerId}", produces="application/json", consumes="application/json")
    public ResponseEntity<PlayerProfileDTO> getPlayerProfile(@PathVariable UUID playerId) {
        return Optional.ofNullable(playerId)
            .flatMap(playerProfileService::findPlayerProfile)
            .map(ResourceUtils::ok)
            .orElseGet(ResourceUtils::notFound);
    }

    @PostMapping(value = "", produces="application/json", consumes="application/json")
    public ResponseEntity<SavedPlayerProfileResponse> addPlayerProfile(@RequestBody PlayerProfileDTO playerProfile) {
        if (isPlayerProfileInvalid(playerProfile)) {
            return ResourceUtils.badRequest();
        }
        return playerProfileService.savePlayerProfile(playerProfile)
            .map(PlayerProfileResource::toSavedPlayerProfileResponse)
            .map(ResourceUtils::created)
            .orElseGet(ResourceUtils::serverError);
    }

    @PostMapping(value = "/{playerId}/match", produces="application/json")
    public ResponseEntity<PlayerProfileDTO> matchPlayerProfile(@PathVariable UUID playerId) {
        return playerProfileMatcherService.matchPlayerProfile(playerId)
            .map(ResourceUtils::ok)
            .orElseGet(ResourceUtils::notFound);
    }

    private static SavedPlayerProfileResponse toSavedPlayerProfileResponse(UUID playerId) {
        return new SavedPlayerProfileResponse(playerId)
            .add(linkTo(
                methodOn(PlayerProfileResource.class).getPlayerProfile(playerId)
            ).withSelfRel())
            .add(linkTo(
                methodOn(PlayerProfileResource.class).matchPlayerProfile(playerId)
            ).withSelfRel());
    }

    private static boolean isDateTimeIntervalInvalid(LocalDate afterDate, LocalDate beforeDate) {
        return Period.between(afterDate, beforeDate).isNegative();
    }

    private static boolean isPlayerProfileInvalid(PlayerProfileDTO playerProfile) {
        return Optional.ofNullable(playerProfile)
            .map(PlayerProfileDTO::isInvalid)
            .orElse(Boolean.FALSE);
    }
}
