package com.bibek.hms.controllers;

import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.dto.CreatePatientRequestDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import com.bibek.hms.services.IPatientService;
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
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Patient api healthy 🏥⚡️");
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody CreatePatientRequestDTO requestDTO) {
        PatientResponseDTO patient = patientService.createPatient(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<PatientResponseDTO> patients = patientService.getPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable UUID uuid) {
        return patientService.getPatientById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PatientResponseDTO> getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PatientResponseDTO>> searchPatientsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<PatientResponseDTO> patients = patientService.searchPatientsByName(name, pageable);
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID uuid,
            @RequestBody CreatePatientRequestDTO requestDTO
    ) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(uuid, requestDTO);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID uuid) {
        patientService.deletePatient(uuid);
        return ResponseEntity.noContent().build();
    }
}

