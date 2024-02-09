package com.hunus.playerprofileservice.dto.campaign;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private String game;
    private String name;
    private double priority;
    private MatchersDTO matchers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean enabled;
    private LocalDateTime lastUpdated;
}
