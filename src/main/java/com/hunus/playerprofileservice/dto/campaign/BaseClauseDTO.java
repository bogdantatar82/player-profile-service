package com.hunus.playerprofileservice.dto.campaign;

import java.util.List;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "clauseType",
    defaultImpl = HasClauseDTO.class,
    visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = HasClauseDTO.class, name = "HAS"),
    @JsonSubTypes.Type(value = DoesNotHaveClauseDTO.class, name = "DOES_NOT_HAVE"),
})
public abstract class BaseClauseDTO implements Predicate<PlayerProfileDTO> {
    private List<String> country;
    private List<String> items;
    private ClauseType clauseType;

    protected boolean hasItemsMatch(PlayerProfileDTO playerProfile) {
        if (isEmptyList(items)) {
            return Boolean.TRUE;
        }
        return items.stream()
            .map(item -> playerProfile.getInventory().containsKey(item))
            .reduce((b1, b2) -> b1 && b2)
            .orElse(Boolean.FALSE);
    }

    protected boolean hasCountryMatch(PlayerProfileDTO playerProfile) {
        if (isEmptyList(country)) {
            return Boolean.TRUE;
        }
        return country.contains(playerProfile.getCountry());
    }

    protected boolean isClauseInvalid() {
        return isEmptyList(country) && isEmptyList(items);
    }

    private static boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    public enum ClauseType {
        HAS, DOES_NOT_HAVE
    }
}
