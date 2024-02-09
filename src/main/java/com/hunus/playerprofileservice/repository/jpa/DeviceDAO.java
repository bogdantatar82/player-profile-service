package com.hunus.playerprofileservice.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hunus.playerprofileservice.database.jpa.Device;

@Repository
public interface DeviceDAO extends JpaRepository<Device, UUID> {
    Optional<Device> findByModel(String model);
}