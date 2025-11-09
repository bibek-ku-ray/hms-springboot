package com.bibek.hms.repositories;


import com.bibek.hms.enities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findByEmail(String email);
    Page<Doctor> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Doctor> findBySpecialization(String specialization, Pageable pageable);
}
