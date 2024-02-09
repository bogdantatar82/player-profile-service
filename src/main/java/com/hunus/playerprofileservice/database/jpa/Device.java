package com.hunus.playerprofileservice.database.jpa;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.hunus.playerprofileservice.dto.DeviceDTO;

@Entity
@Table(name = "device")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "model", nullable = false)
    @EqualsAndHashCode.Exclude
    private String model;

    @Column(name = "carrier", nullable = false)
    @EqualsAndHashCode.Exclude
    private String carrier;

    @Column(name = "firmware", nullable = false)
    @EqualsAndHashCode.Exclude
    private String firmware;

    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime created;

    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime modified;

    // the OWNER side in the bidirectional relationship
    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    public Device(String model, String carrier, String firmware) {
        this.model = model;
        this.carrier = carrier;
        this.firmware = firmware;
    }

    @PrePersist
    @PreUpdate
    private void onSave() {
        if (id == null) {
            id = UUID.randomUUID();
            created = LocalDateTime.now();
        }
        modified = LocalDateTime.now();
    }

    public static DeviceDTO toDeviceDTO(Device input) {
        return Optional.ofNullable(input)
            .map(device ->  new DeviceDTO(input.getId(), input.getModel(), input.getCarrier(), input.getFirmware()))
            .orElse(null);
    }
}
