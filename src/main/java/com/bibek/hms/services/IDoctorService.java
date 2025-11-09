package com.bibek.hms.services;

import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.dto.CreateDoctorRequestDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDoctorService {
    DoctorResponseDTO createDoctor(CreateDoctorRequestDTO doctorRequestDTO);
    List<DoctorResponseDTO> getDoctors();
    Page<DoctorResponseDTO> getDoctors(Pageable pageable);
    Optional<DoctorResponseDTO> getDoctorById(UUID id);
    Optional<DoctorResponseDTO> getDoctorByEmail(String email);
    Page<DoctorResponseDTO> searchDoctorsByName(String name, Pageable pageable);
    Page<DoctorResponseDTO> getDoctorsBySpecialization(String specialization, Pageable pageable);
    DoctorResponseDTO updateDoctor(UUID id, CreateDoctorRequestDTO doctorRequestDTO);
    void deleteDoctor(UUID id);
    List<PatientResponseDTO> getPatientsByDoctor(UUID doctorId);
    List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId);
    DoctorResponseDTO assignPatientToDoctor(UUID doctorId, UUID patientId);
}
