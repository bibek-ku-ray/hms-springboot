package com.bibek.hms.controllers;

import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.dto.CreateDoctorRequestDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.dto.PatientResponseDTO;
import com.bibek.hms.services.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final IDoctorService doctorService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Doctor api healthy 🥼⚡️");
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody CreateDoctorRequestDTO requestDTO) {
        DoctorResponseDTO doctor = doctorService.createDoctor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDTO>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<DoctorResponseDTO> doctors = doctorService.getDoctors(pageable);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable UUID uuid) {
        return doctorService.getDoctorById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DoctorResponseDTO> getDoctorByEmail(@PathVariable String email) {
        return doctorService.getDoctorByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DoctorResponseDTO>> searchDoctorsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<DoctorResponseDTO> doctors = doctorService.searchDoctorsByName(name, pageable);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<Page<DoctorResponseDTO>> getDoctorsBySpecialization(
            @PathVariable String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<DoctorResponseDTO> doctors = doctorService.getDoctorsBySpecialization(specialization, pageable);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByDoctor(@PathVariable UUID id) {
        List<PatientResponseDTO> patients = doctorService.getPatientsByDoctor(id);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDoctor(@PathVariable UUID id) {
        List<AppointmentResponseDTO> appointments = doctorService.getAppointmentsByDoctor(id);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/{id}/patients/{patientId}")
    public ResponseEntity<DoctorResponseDTO> assignPatientToDoctor(
            @PathVariable UUID id,
            @PathVariable UUID patientId
    ) {
        DoctorResponseDTO doctor = doctorService.assignPatientToDoctor(id, patientId);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(
            @PathVariable UUID uuid,
            @RequestBody CreateDoctorRequestDTO requestDTO
    ) {
        DoctorResponseDTO doctorResponseDTO = doctorService.updateDoctor(uuid, requestDTO);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID uuid) {
        doctorService.deleteDoctor(uuid);
        return ResponseEntity.noContent().build();
    }
}
