package com.bibek.hms.services;

import com.bibek.hms.dto.CreateAppointmentRequestDTO;
import com.bibek.hms.dto.AppointmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAppointmentService {
    AppointmentResponseDTO createAppointment(CreateAppointmentRequestDTO requestDTO);
    List<AppointmentResponseDTO> getAppointments();
    Page<AppointmentResponseDTO> getAppointments(Pageable pageable);
    Optional<AppointmentResponseDTO> getAppointmentById(UUID id);
    List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId);
    List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId);
    Page<AppointmentResponseDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable);
    AppointmentResponseDTO updateAppointment(UUID id, CreateAppointmentRequestDTO requestDTO);
    void deleteAppointment(UUID id);
}

