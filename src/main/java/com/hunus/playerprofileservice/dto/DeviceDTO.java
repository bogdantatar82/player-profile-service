package com.hunus.playerprofileservice.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private UUID id;
    private String model;
    private String carrier;
    private String firmware;

    public DeviceDTO(String model, String carrier, String firmware) {
        this.model = model;
        this.carrier = carrier;
        this.firmware = firmware;
    }
}
