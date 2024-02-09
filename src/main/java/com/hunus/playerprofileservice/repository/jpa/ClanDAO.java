package com.hunus.playerprofileservice.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hunus.playerprofileservice.database.jpa.Clan;

@Repository
public interface ClanDAO extends JpaRepository<Clan, UUID> {

    Optional<Clan> findByName(String name);
}
