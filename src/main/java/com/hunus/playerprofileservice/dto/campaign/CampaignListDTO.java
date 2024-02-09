package com.hunus.playerprofileservice.dto.campaign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignListDTO {
    private List<CampaignDTO> campaigns;
}
