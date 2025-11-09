package com.bibek.hms.services.impl;

import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.dto.CreateDoctorRequestDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import com.bibek.hms.enities.Doctor;
import com.bibek.hms.enities.Patient;
import com.bibek.hms.mapper.AppointmentMapper;
import com.bibek.hms.mapper.DoctorMapper;
import com.bibek.hms.mapper.PatientMapper;
import com.bibek.hms.repositories.DoctorRepository;
import com.bibek.hms.repositories.PatientRepository;
import com.bibek.hms.services.IDoctorService;
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
public class DoctorServiceImpl implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(CreateDoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = Doctor.builder()
                .name(doctorRequestDTO.getName())
                .specialization(doctorRequestDTO.getSpecialization())
                .email(doctorRequestDTO.getEmail())
                .build();
        doctorRepository.save(doctor);
        return DoctorMapper.toResponse(doctor);
    }

    @Override
    public List<DoctorResponseDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(DoctorMapper::toResponse).toList();
    }

    @Override
    public Page<DoctorResponseDTO> getDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll(pageable);
        return doctors.map(DoctorMapper::toResponse);
    }

    @Override
    public Optional<DoctorResponseDTO> getDoctorById(UUID uuid) {
        Optional<Doctor> doctorById = doctorRepository.findById(uuid);
        return doctorById.map(DoctorMapper::toResponse);
    }

    @Override
    public Optional<DoctorResponseDTO> getDoctorByEmail(String email) {
        Optional<Doctor> doctorByEmail = doctorRepository.findByEmail(email);
        return doctorByEmail.map(DoctorMapper::toResponse);
    }

    @Override
    public Page<DoctorResponseDTO> searchDoctorsByName(String name, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name, pageable);
        return doctors.map(DoctorMapper::toResponse);
    }

    @Override
    public Page<DoctorResponseDTO> getDoctorsBySpecialization(String specialization, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findBySpecialization(specialization, pageable);
        return doctors.map(DoctorMapper::toResponse);
    }

    @Override
    @Transactional
    public DoctorResponseDTO updateDoctor(UUID id, CreateDoctorRequestDTO doctorRequestDTO) {
        Optional<Doctor> doctorById = doctorRepository.findById(id);

        if (doctorById.isPresent()){
            Doctor doctor = doctorById.get();
            doctor.setName(doctorRequestDTO.getName());
            doctor.setEmail(doctorRequestDTO.getEmail());
            doctor.setSpecialization(doctorRequestDTO.getSpecialization());
            doctorRepository.save(doctor);
            return DoctorMapper.toResponse(doctor);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteDoctor(UUID id) {
        Optional<Doctor> doctorById = doctorRepository.findById(id);
        if (doctorById.isPresent()){
            doctorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    @Override
    public List<PatientResponseDTO> getPatientsByDoctor(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        return doctor.getPatients().stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        return doctor.getAppointments().stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DoctorResponseDTO assignPatientToDoctor(UUID doctorId, UUID patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        patient.setDoctor(doctor);
        patientRepository.save(patient);

        return DoctorMapper.toResponse(doctor);
    }
}
