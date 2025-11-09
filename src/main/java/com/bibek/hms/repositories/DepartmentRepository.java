package com.bibek.hms.repositories;

import com.bibek.hms.enities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByName(String name);
    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

