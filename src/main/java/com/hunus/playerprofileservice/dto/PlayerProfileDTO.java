package com.hunus.playerprofileservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProfileDTO {
    private UUID playerId;
    private String credential;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastSession;
    private int totalSpent;
    private int totalRefund;
    private int totalTransactions;
    private LocalDateTime lastPurchase;
    private List<String> activeCampaigns;
    private int level;
    private int experience;
    private int totalPlaytime;
    private String country;
    private String language;
    private LocalDateTime birthdate;
    private String gender;
    private Map<String, Integer> inventory;
    private String customField;
    private ClanDTO clan;
    private List<DeviceDTO> devices;

    //TODO do some more validations in here
    public boolean isInvalid() {
        return (credential == null || credential.isBlank()) ||
            (clan == null || clan.isInvalid());
    }
}
