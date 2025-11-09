package com.bibek.hms.mapper;

import com.bibek.hms.dto.CreatePatientRequestDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import com.bibek.hms.enities.Patient;

public class PatientMapper {
    public static Patient toEntity(CreatePatientRequestDTO dto) {
        return Patient.builder()
                .name(dto.getName())
                .dob(dto.getDob())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .bloodType(dto.getBloodType())
                .build();
    }

    public static PatientResponseDTO toResponse(Patient patient) {
        return PatientResponseDTO.builder()
                .uuid(patient.getUuid())
                .name(patient.getName())
                .dob(patient.getDob())
                .email(patient.getEmail())
                .gender(patient.getGender())
                .bloodType(patient.getBloodType())
                .doctorId(patient.getDoctor() != null ? patient.getDoctor().getUuid() : null)
                .doctorName(patient.getDoctor() != null ? patient.getDoctor().getName() : null)
                .insuranceId(patient.getInsurance() != null ? patient.getInsurance().getUuid() : null)
                .build();
    }
}

