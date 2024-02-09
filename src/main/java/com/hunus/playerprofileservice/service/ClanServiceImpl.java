package com.hunus.playerprofileservice.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hunus.playerprofileservice.repository.jpa.ClanDAO;
import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.database.jpa.Clan;

@Service
public class ClanServiceImpl implements ClanService {
    private static final Logger logger = LoggerFactory.getLogger(ClanServiceImpl.class);
    private final ClanDAO clanDAO;

    @Autowired
    public ClanServiceImpl(ClanDAO clanDAO) {
        this.clanDAO = clanDAO;
    }

    @Override
    public Optional<UUID> saveClan(ClanDTO clanDTO) {
        try {
            return toClan(clanDTO)
                .map(clanDAO::save)
                .map(Clan::getId);
        } catch (Exception ex) {
            logger.error("Error occurred when saving clan:" + clanDTO, ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClanDTO> findClanById(UUID clanId) {
        try {
            return clanDAO.findById(clanId)
                .map(Clan::toClanDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while retrieving clan with id:" + clanId, ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClanDTO> findClanByIdOrName(UUID clanId, String clanName) {
        return Optional.ofNullable(clanId)
            .map(clanDAO::findById)
            .orElseGet(() -> Optional.ofNullable(clanName)
                .map(clanDAO::findByName)
                .orElseThrow()
            ).flatMap(ClanServiceImpl::toClanDTO);
    }

    private static Optional<Clan> toClan(ClanDTO input) {
        return Optional.ofNullable(input)
            .map(clan -> new Clan(input.getName()));
    }

    private static Optional<ClanDTO> toClanDTO(Clan input) {
        return Optional.ofNullable(input)
            .map(clan -> new ClanDTO(input.getName()));
    }
}
