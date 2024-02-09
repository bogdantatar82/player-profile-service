package com.hunus.playerprofileservice.dto.campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchersDTO {
    private LevelDTO level;
    private HasClauseDTO has;
    private DoesNotHaveClauseDTO doesNotHave;

    public List<Predicate<PlayerProfileDTO>> findMatchers() {
        List<Predicate<PlayerProfileDTO>> matchers = new ArrayList<>();
        if (level != null) {
            matchers.add(level);
        }
        if (has != null) {
            matchers.add(has);
        }
        if (doesNotHave != null) {
            matchers.add(doesNotHave);
        }
        return matchers;
    }
}
