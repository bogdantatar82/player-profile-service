package com.hunus.playerprofileservice.dto.campaign;

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
public class LevelDTO implements Predicate<PlayerProfileDTO> {
    private int min;
    private int max;

    @Override
    public boolean test(PlayerProfileDTO playerProfile) {
        return min <= playerProfile.getLevel() && max >= playerProfile.getLevel();
    }
}
