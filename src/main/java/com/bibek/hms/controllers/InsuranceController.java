package com.bibek.hms.controllers;

import com.bibek.hms.dto.CreateInsuranceRequestDTO;
import com.bibek.hms.dto.InsuranceResponseDTO;
import com.bibek.hms.services.IInsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final IInsuranceService insuranceService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Insurance api healthy 🛡️⚡️");
    }

    @PostMapping
    public ResponseEntity<InsuranceResponseDTO> createInsurance(@RequestBody CreateInsuranceRequestDTO requestDTO) {
        InsuranceResponseDTO insurance = insuranceService.createInsurance(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(insurance);
    }

    @GetMapping
    public ResponseEntity<Page<InsuranceResponseDTO>> getAllInsurances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "providerName") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<InsuranceResponseDTO> insurances = insuranceService.getInsurances(pageable);
        return ResponseEntity.ok(insurances);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InsuranceResponseDTO> getInsuranceById(@PathVariable UUID uuid) {
        return insuranceService.getInsuranceById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/policy/{policyNumber}")
    public ResponseEntity<InsuranceResponseDTO> getInsuranceByPolicyNumber(@PathVariable String policyNumber) {
        return insuranceService.getInsuranceByPolicyNumber(policyNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InsuranceResponseDTO>> searchInsurancesByProvider(
            @RequestParam String providerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "providerName") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<InsuranceResponseDTO> insurances = insuranceService.searchInsurancesByProvider(providerName, pageable);
        return ResponseEntity.ok(insurances);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<InsuranceResponseDTO> updateInsurance(
            @PathVariable UUID uuid,
            @RequestBody CreateInsuranceRequestDTO requestDTO
    ) {
        InsuranceResponseDTO insuranceResponseDTO = insuranceService.updateInsurance(uuid, requestDTO);
        return ResponseEntity.ok(insuranceResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable UUID uuid) {
        insuranceService.deleteInsurance(uuid);
        return ResponseEntity.noContent().build();
    }
}

