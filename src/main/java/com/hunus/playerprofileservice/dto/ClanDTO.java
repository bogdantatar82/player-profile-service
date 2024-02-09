package com.hunus.playerprofileservice.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClanDTO {
    private UUID id;
    private String name;

    public ClanDTO(String name) {
        this.name = name;
    }

    public boolean isInvalid() {
        return id == null && (name == null || name.isBlank());
    }
}
