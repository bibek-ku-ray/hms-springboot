package com.bibek.hms.services;

import com.bibek.hms.dto.CreateInsuranceRequestDTO;
import com.bibek.hms.dto.InsuranceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInsuranceService {
    InsuranceResponseDTO createInsurance(CreateInsuranceRequestDTO requestDTO);
    List<InsuranceResponseDTO> getInsurances();
    Page<InsuranceResponseDTO> getInsurances(Pageable pageable);
    Optional<InsuranceResponseDTO> getInsuranceById(UUID id);
    Optional<InsuranceResponseDTO> getInsuranceByPolicyNumber(String policyNumber);
    Page<InsuranceResponseDTO> searchInsurancesByProvider(String providerName, Pageable pageable);
    InsuranceResponseDTO updateInsurance(UUID id, CreateInsuranceRequestDTO requestDTO);
    void deleteInsurance(UUID id);
}

