package com.hunus.playerprofileservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hunus.playerprofileservice.repository.jpa.ClanDAO;
import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.database.jpa.Clan;

@ExtendWith(MockitoExtension.class)
public class ClanServiceImplTest {
    @Mock
    private ClanDAO clanDAO;
    @Captor
    private ArgumentCaptor<Clan> clanArgumentCaptor;
    private ClanService service;

    @BeforeEach
    public void setup() {
        service = new ClanServiceImpl(clanDAO);
    }

    @Test
    void saveClan_returnsEmptyWhenNullInput() {
        assertTrue(service.saveClan(null).isEmpty());
    }

    @Test
    void saveClan_returnsSavedClanId() {
        // given
        UUID clanId = UUID.randomUUID();
        ClanDTO clanDTO = mock(ClanDTO.class);
        Clan clan = mock(Clan.class);
        when(clan.getId()).thenReturn(clanId);
        when(clanDAO.save(any(Clan.class))).thenReturn(clan);

        // when
        Optional<UUID> savedClanId = service.saveClan(clanDTO);

        // then
        assertTrue(savedClanId.isPresent());
        assertEquals(clanId, savedClanId.get());
    }

    @Test
    void saveClan_returnsNoClanIdWhenExceptionIsThrown() {
        // given
        ClanDTO clanDTO = mock(ClanDTO.class);
        when(clanDAO.save(any(Clan.class))).thenThrow(new RuntimeException());

        // when
        Optional<UUID> savedPlayerId = service.saveClan(clanDTO);

        // then
        assertTrue(savedPlayerId.isEmpty());
    }
}
