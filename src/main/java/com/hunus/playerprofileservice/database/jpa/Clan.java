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

import com.hunus.playerprofileservice.dto.ClanDTO;

@Entity
@Table(name = "clan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clan {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String name;

    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime created;

    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime modified;

    public Clan(String name) {
        this.name = name;
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

    public static ClanDTO toClanDTO(Clan input) {
        return Optional.ofNullable(input)
            .map(clan -> new ClanDTO(input.getId(), input.getName()))
            .orElse(null);
    }
}
