package com.bibek.hms.services.impl;

import com.bibek.hms.dto.CreatePatientRequestDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import com.bibek.hms.enities.Doctor;
import com.bibek.hms.enities.Patient;
import com.bibek.hms.mapper.PatientMapper;
import com.bibek.hms.repositories.DoctorRepository;
import com.bibek.hms.repositories.PatientRepository;
import com.bibek.hms.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(CreatePatientRequestDTO requestDTO) {
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + requestDTO.getDoctorId()));

        Patient patient = PatientMapper.toEntity(requestDTO);
        patient.setDoctor(doctor);
        patientRepository.save(patient);

        return PatientMapper.toResponse(patient);
    }

    @Override
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toResponse).toList();
    }

    @Override
    public Page<PatientResponseDTO> getPatients(Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.map(PatientMapper::toResponse);
    }

    @Override
    public Optional<PatientResponseDTO> getPatientById(UUID id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(PatientMapper::toResponse);
    }

    @Override
    public Optional<PatientResponseDTO> getPatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        return patient.map(PatientMapper::toResponse);
    }

    @Override
    public Page<PatientResponseDTO> searchPatientsByName(String name, Pageable pageable) {
        Page<Patient> patients = patientRepository.findByNameContainingIgnoreCase(name, pageable);
        return patients.map(PatientMapper::toResponse);
    }

    @Override
    public List<PatientResponseDTO> getPatientsByDoctor(UUID doctorId) {
        List<Patient> patients = patientRepository.findByDoctorUuid(doctorId);
        return patients.stream().map(PatientMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(UUID id, CreatePatientRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        if (requestDTO.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + requestDTO.getDoctorId()));
            patient.setDoctor(doctor);
        }

        patient.setName(requestDTO.getName());
        patient.setDob(requestDTO.getDob());
        patient.setEmail(requestDTO.getEmail());
        patient.setGender(requestDTO.getGender());
        patient.setBloodType(requestDTO.getBloodType());

        patientRepository.save(patient);
        return PatientMapper.toResponse(patient);
    }

    @Override
    @Transactional
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
}

