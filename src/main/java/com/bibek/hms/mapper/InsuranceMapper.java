package com.bibek.hms.mapper;

import com.bibek.hms.dto.CreateInsuranceRequestDTO;
import com.bibek.hms.dto.InsuranceResponseDTO;
import com.bibek.hms.enities.Insurance;

public class InsuranceMapper {
    public static Insurance toEntity(CreateInsuranceRequestDTO dto) {
        return Insurance.builder()
                .policyNumber(dto.getPolicyNumber())
                .providerName(dto.getProviderName())
                .validUntil(dto.getValidUntil())
                .build();
    }

    public static InsuranceResponseDTO toResponse(Insurance insurance) {
        return InsuranceResponseDTO.builder()
                .uuid(insurance.getUuid())
                .policyNumber(insurance.getPolicyNumber())
                .providerName(insurance.getProviderName())
                .validUntil(insurance.getValidUntil())
                .patientId(insurance.getPatient() != null ? insurance.getPatient().getUuid() : null)
                .patientName(insurance.getPatient() != null ? insurance.getPatient().getName() : null)
                .build();
    }
}

