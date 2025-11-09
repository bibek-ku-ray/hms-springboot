package com.bibek.hms.services.impl;

import com.bibek.hms.dto.CreateInsuranceRequestDTO;
import com.bibek.hms.dto.InsuranceResponseDTO;
import com.bibek.hms.enities.Insurance;
import com.bibek.hms.enities.Patient;
import com.bibek.hms.mapper.InsuranceMapper;
import com.bibek.hms.repositories.InsuranceRepository;
import com.bibek.hms.repositories.PatientRepository;
import com.bibek.hms.services.IInsuranceService;
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
public class InsuranceServiceImpl implements IInsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public InsuranceResponseDTO createInsurance(CreateInsuranceRequestDTO requestDTO) {
        Insurance insurance = InsuranceMapper.toEntity(requestDTO);

        if (requestDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(requestDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + requestDTO.getPatientId()));

            if (patient.getInsurance() != null) {
                throw new RuntimeException("Patient already has insurance");
            }

            insurance.setPatient(patient);
            patient.setInsurance(insurance);
        }

        insuranceRepository.save(insurance);
        return InsuranceMapper.toResponse(insurance);
    }

    @Override
    public List<InsuranceResponseDTO> getInsurances() {
        List<Insurance> insurances = insuranceRepository.findAll();
        return insurances.stream().map(InsuranceMapper::toResponse).toList();
    }

    @Override
    public Page<InsuranceResponseDTO> getInsurances(Pageable pageable) {
        Page<Insurance> insurances = insuranceRepository.findAll(pageable);
        return insurances.map(InsuranceMapper::toResponse);
    }

    @Override
    public Optional<InsuranceResponseDTO> getInsuranceById(UUID id) {
        Optional<Insurance> insurance = insuranceRepository.findById(id);
        return insurance.map(InsuranceMapper::toResponse);
    }

    @Override
    public Optional<InsuranceResponseDTO> getInsuranceByPolicyNumber(String policyNumber) {
        Optional<Insurance> insurance = insuranceRepository.findByPolicyNumber(policyNumber);
        return insurance.map(InsuranceMapper::toResponse);
    }

    @Override
    public Page<InsuranceResponseDTO> searchInsurancesByProvider(String providerName, Pageable pageable) {
        Page<Insurance> insurances = insuranceRepository.findByProviderNameContainingIgnoreCase(providerName, pageable);
        return insurances.map(InsuranceMapper::toResponse);
    }

    @Override
    @Transactional
    public InsuranceResponseDTO updateInsurance(UUID id, CreateInsuranceRequestDTO requestDTO) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found with id: " + id));

        insurance.setPolicyNumber(requestDTO.getPolicyNumber());
        insurance.setProviderName(requestDTO.getProviderName());
        insurance.setValidUntil(requestDTO.getValidUntil());

        if (requestDTO.getPatientId() != null &&
            (insurance.getPatient() == null || !insurance.getPatient().getUuid().equals(requestDTO.getPatientId()))) {

            Patient patient = patientRepository.findById(requestDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + requestDTO.getPatientId()));

            if (patient.getInsurance() != null && !patient.getInsurance().getUuid().equals(id)) {
                throw new RuntimeException("Patient already has insurance");
            }

            insurance.setPatient(patient);
            patient.setInsurance(insurance);
        }

        insuranceRepository.save(insurance);
        return InsuranceMapper.toResponse(insurance);
    }

    @Override
    @Transactional
    public void deleteInsurance(UUID id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found with id: " + id));
        insuranceRepository.delete(insurance);
    }
}

