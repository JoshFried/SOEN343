package com.soen343.shs.dal.repository;

import com.soen343.shs.dal.model.SecuritySystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecuritySystemRepository extends JpaRepository<SecuritySystem, Long> {
    Optional<SecuritySystem> findByHouseId(long houseId);
}

