package com.hunus.playerprofileservice.repository.jooq;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.jooq.DSLContext;
import org.jooq.Record4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import com.hunus.playerprofileservice.database.jooq.model.tables.Clans;
import com.hunus.playerprofileservice.dto.ClanDTO;

@Repository
@Slf4j
public class ClanRepository {
    private final DSLContext db;

    @Autowired
    public ClanRepository(DSLContext db) {
        this.db = db;
    }

    public UUID insertClan(ClanDTO clan) {
        if (clan.getId() == null) {
            clan.setId(UUID.randomUUID());
        }
        try {
            db.insertInto(
                Clans.CLANS,
                Clans.CLANS.ID,
                Clans.CLANS.NAME
            ).values(
                clan.getId(),
                clan.getName()
            ).execute();
        } catch (DuplicateKeyException e) {
            log.debug("Clan {} already inserted", clan.getId());
        }
        return clan.getId();
    }

    public Optional<ClanDTO> findById(UUID clanId) {
        return db.select(Clans.CLANS.ID,
                Clans.CLANS.NAME,
                Clans.CLANS.CREATED,
                Clans.CLANS.MODIFIED)
            .from(Clans.CLANS)
            .where(Clans.CLANS.ID.eq(clanId))
            .fetchOptional()
            .map(ClanRepository::recordToClan);
    }

    public Optional<ClanDTO> findByName(String clanName) {
        return db.select(Clans.CLANS.ID,
                Clans.CLANS.NAME,
                Clans.CLANS.CREATED,
                Clans.CLANS.MODIFIED)
            .from(Clans.CLANS)
            .where(Clans.CLANS.NAME.eq(clanName))
            .fetchOptional()
            .map(ClanRepository::recordToClan);
    }

    private static ClanDTO recordToClan(Record4<UUID, String, LocalDateTime, LocalDateTime> clanRecord) {
        return new ClanDTO(clanRecord.get(Clans.CLANS.NAME));
    }
}

