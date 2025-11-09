package com.bibek.hms.services;

import com.bibek.hms.dto.CreatePatientRequestDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPatientService {
    PatientResponseDTO createPatient(CreatePatientRequestDTO requestDTO);
    List<PatientResponseDTO> getPatients();
    Page<PatientResponseDTO> getPatients(Pageable pageable);
    Optional<PatientResponseDTO> getPatientById(UUID id);
    Optional<PatientResponseDTO> getPatientByEmail(String email);
    Page<PatientResponseDTO> searchPatientsByName(String name, Pageable pageable);
    List<PatientResponseDTO> getPatientsByDoctor(UUID doctorId);
    PatientResponseDTO updatePatient(UUID id, CreatePatientRequestDTO requestDTO);
    void deletePatient(UUID id);
}

