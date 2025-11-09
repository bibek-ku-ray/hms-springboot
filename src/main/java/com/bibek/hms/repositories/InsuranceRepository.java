package com.bibek.hms.repositories;

import com.bibek.hms.enities.Insurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InsuranceRepository extends JpaRepository<Insurance, UUID> {
    Optional<Insurance> findByPolicyNumber(String policyNumber);
    Page<Insurance> findByProviderNameContainingIgnoreCase(String providerName, Pageable pageable);
}

