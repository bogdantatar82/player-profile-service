package com.hunus.playerprofileservice.dto.response;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SavedPlayerProfileResponse extends RepresentationModel<SavedPlayerProfileResponse> {
    private UUID playerId;
}
