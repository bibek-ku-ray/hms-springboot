package com.bibek.hms.repositories;

import com.bibek.hms.enities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByEmail(String email);
    List<Patient> findByDoctorUuid(UUID doctorId);
    Page<Patient> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Patient> findByDoctorUuid(UUID doctorId, Pageable pageable);
}

