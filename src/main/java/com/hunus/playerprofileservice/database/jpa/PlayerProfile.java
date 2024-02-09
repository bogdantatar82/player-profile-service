package com.hunus.playerprofileservice.database.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.hunus.playerprofileservice.converter.ListConverter;
import com.hunus.playerprofileservice.converter.MapConverter;
import com.hunus.playerprofileservice.dto.DeviceDTO;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

@Entity
@Table(name = "player_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfile {
    @Id
    @Column(name = "id", nullable = false)
    private UUID playerId;
    @Column(name = "credential", nullable = false)
    @EqualsAndHashCode.Exclude
    private String credential;
    @Column(name = "last_session")
    @EqualsAndHashCode.Exclude
    private LocalDateTime lastSession;
    @Column(name = "total_spent")
    @EqualsAndHashCode.Exclude
    private int totalSpent;
    @Column(name = "total_refund")
    @EqualsAndHashCode.Exclude
    private int totalRefund;
    @Column(name = "total_transactions")
    @EqualsAndHashCode.Exclude
    private int totalTransactions;
    @Column(name = "last_purchase")
    @EqualsAndHashCode.Exclude
    private LocalDateTime lastPurchase;
    @Column(name = "active_campaigns")
    @Convert(converter = ListConverter.class)
    @EqualsAndHashCode.Exclude
    private List<String> activeCampaigns = new ArrayList<>();
    @Column(name = "level")
    @EqualsAndHashCode.Exclude
    private int level;
    @Column(name = "experience")
    @EqualsAndHashCode.Exclude
    private int experience;
    @Column(name = "total_playtime")
    @EqualsAndHashCode.Exclude
    private int totalPlaytime;
    @Column(name = "country", nullable = false)
    @EqualsAndHashCode.Exclude
    private String country;
    @Column(name = "language", nullable = false)
    @EqualsAndHashCode.Exclude
    private String language;
    @Column(name = "birthdate", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime birthdate;
    @Column(name = "gender", nullable = false)
    @EqualsAndHashCode.Exclude
    private String gender;
    @Column(name = "inventory")
    @EqualsAndHashCode.Exclude
    @Convert(converter = MapConverter.class)
    private Map<String, Integer> inventory;
    @Column(name = "custom_field")
    @EqualsAndHashCode.Exclude
    private String customField;
    @Column(name = "created", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime created;
    @Column(name = "modified", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime modified;
    @OneToOne
    @JoinColumn(name = "clan_id")
    @EqualsAndHashCode.Exclude
    private Clan clan;
    // the OWNED side in the bidirectional relationship
    @OneToMany(mappedBy = "playerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<Device> devices = new ArrayList<>();

    public PlayerProfile withActiveCampaigns(List<String> activeCampaigns) {
        this.activeCampaigns = activeCampaigns;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlayerProfile.class.getSimpleName() + "{", "}")
            .add("playerId=" + playerId)
            .toString();
    }

    @PrePersist
    @PreUpdate
    private void onSave() {
        if (playerId == null) {
            playerId = UUID.randomUUID();
            devices.forEach(device -> device.setPlayerId(playerId));
            created = LocalDateTime.now();
        }
        modified = LocalDateTime.now();
    }

    public static PlayerProfileDTO toPlayerProfileDTO(PlayerProfile input) {
        return PlayerProfileDTO.builder()
            .playerId(input.getPlayerId())
            .credential(input.getCredential())
            .created(input.getCreated())
            .modified(input.getModified())
            .lastSession(input.getLastSession())
            .totalSpent(input.getTotalSpent())
            .totalRefund(input.getTotalRefund())
            .totalTransactions(input.getTotalTransactions())
            .lastPurchase(input.getLastPurchase())
            .activeCampaigns(input.getActiveCampaigns())
            .level(input.getLevel())
            .experience(input.getExperience())
            .totalPlaytime(input.getTotalPlaytime())
            .country(input.getCountry())
            .language(input.getLanguage())
            .birthdate(input.getBirthdate())
            .gender(input.getGender())
            .inventory(input.getInventory())
            .customField(input.getCustomField())
            .clan(Clan.toClanDTO(input.getClan()))
            .devices(toDeviceDTOs(input.getDevices()))
            .build();
    }

    private static List<DeviceDTO> toDeviceDTOs(List<Device> devices) {
        return Optional.ofNullable(devices)
            .map(list -> list.stream().map(Device::toDeviceDTO).collect(Collectors.toList()))
            .orElse(null);
    }
}
