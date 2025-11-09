package com.bibek.hms.mapper;

import com.bibek.hms.dto.CreateDoctorRequestDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.enities.Doctor;

public class DoctorMapper {
    public static Doctor toEntity(CreateDoctorRequestDTO dto) {
        return Doctor.builder()

                .name(dto.getName())
                .email(dto.getEmail())
                .specialization(dto.getSpecialization())
                .build();
    }

    public static DoctorResponseDTO toResponse(Doctor doctor) {
        return DoctorResponseDTO.builder()
                .uuid(doctor.getUuid())
                .email(doctor.getEmail())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .build();
    }

}
