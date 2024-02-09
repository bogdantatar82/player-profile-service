package com.hunus.playerprofileservice.repository.jpa;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hunus.playerprofileservice.database.jpa.PlayerProfile;

@Repository
public interface PlayerProfileDAO extends JpaRepository<PlayerProfile, UUID>, JpaSpecificationExecutor<PlayerProfile> {
    String GET_PLAYER_PROFILES_BY_LAST_SESSION = "SELECT pp FROM PlayerProfile pp " +
        "JOIN Device d ON pp.playerId = d.playerId JOIN Clan c ON pp.clan = c " +
        "WHERE DATE(pp.lastSession) BETWEEN :lastSessionAfter AND :lastSessionBefore";
    String GET_PLAYER_PROFILES_BY_LAST_SESSION_AND_CUSTOM_FIELD = GET_PLAYER_PROFILES_BY_LAST_SESSION + " AND pp.customField = :customField";

    @Query(GET_PLAYER_PROFILES_BY_LAST_SESSION)
    Page<PlayerProfile> findByLastSession(
        @Param("lastSessionAfter") LocalDate lastSessionAfter,
        @Param("lastSessionBefore") LocalDate lastSessionBefore,
        Pageable pageable
    );

    @Query(GET_PLAYER_PROFILES_BY_LAST_SESSION_AND_CUSTOM_FIELD)
    Page<PlayerProfile> findByCustomFieldAndLastSession(
        @Param("customField") String customField,
        @Param("lastSessionAfter") LocalDate lastSessionAfter,
        @Param("lastSessionBefore") LocalDate lastSessionBefore,
        Pageable pageable
    );
}
