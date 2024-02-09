package com.hunus.playerprofileservice.dto.campaign;

import static com.hunus.playerprofileservice.dto.campaign.BaseClauseDTO.ClauseType.HAS;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

@Data
@NoArgsConstructor
public class HasClauseDTO extends BaseClauseDTO {
    @Builder
    public HasClauseDTO(List<String> country, List<String> items) {
        super(country, items, HAS);
    }

    @Override
    public boolean test(PlayerProfileDTO playerProfile) {
        if (isClauseInvalid()) {
            return false;
        }
        return hasCountryMatch(playerProfile) && hasItemsMatch(playerProfile);
    }
}
