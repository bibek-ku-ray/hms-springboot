package com.bibek.hms.repositories;

import com.bibek.hms.enities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByDoctorUuid(UUID doctorId);
    List<Appointment> findByPatientUuid(UUID patientId);
    Page<Appointment> findByDoctorUuid(UUID doctorId, Pageable pageable);
    Page<Appointment> findByPatientUuid(UUID patientId, Pageable pageable);
    Page<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}

