package com.hunus.playerprofileservice.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.dto.response.SavedClanResponse;
import com.hunus.playerprofileservice.service.ClanService;

@RestController
@RequestMapping("/v0/clans")
public class ClanResource {
    private final ClanService clanService;

    @Autowired
    public ClanResource(ClanService clanService) {
        this.clanService = clanService;
    }

    @PostMapping(value = "", produces="application/json", consumes="application/json")
    public ResponseEntity<SavedClanResponse> addClan(@RequestBody ClanDTO clan) {
        if (isClanInValid(clan)) {
            return ResourceUtils.badRequest();
        }
        return clanService.saveClan(clan)
            .map(ClanResource::toSavedClanResponse)
            .map(ResourceUtils::created)
            .orElseGet(ResourceUtils::serverError);
    }

    @GetMapping(value = "/{clanId}", produces="application/json", consumes="application/json")
    public ResponseEntity<ClanDTO> getClan(@PathVariable UUID clanId) {
        return Optional.ofNullable(clanId)
            .flatMap(clanService::findClanById)
            .map(ResourceUtils::ok)
            .orElseGet(ResourceUtils::notFound);
    }

    private static SavedClanResponse toSavedClanResponse(UUID clanId) {
        return new SavedClanResponse(clanId)
            .add(linkTo(
                methodOn(ClanResource.class).getClan(clanId)
            ).withSelfRel());
    }

    private static boolean isClanInValid(ClanDTO clanDTO) {
        return Optional.ofNullable(clanDTO)
            .map(ClanDTO::isInvalid)
            .orElse(Boolean.FALSE);
    }
}
