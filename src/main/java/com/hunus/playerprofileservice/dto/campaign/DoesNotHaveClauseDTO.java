package com.hunus.playerprofileservice.dto.campaign;

import static com.hunus.playerprofileservice.dto.campaign.BaseClauseDTO.ClauseType.DOES_NOT_HAVE;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

@Data
@NoArgsConstructor
public class DoesNotHaveClauseDTO extends BaseClauseDTO {
    @Builder
    public DoesNotHaveClauseDTO(List<String> country, List<String> items) {
        super(country, items, DOES_NOT_HAVE);
    }

    @Override
    public boolean test(PlayerProfileDTO playerProfile) {
        if (isClauseInvalid()) {
            return false;
        }
        return !hasCountryMatch(playerProfile) || !hasItemsMatch(playerProfile);
    }
}
