package com.bibek.hms.controllers;

import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.dto.CreateAppointmentRequestDTO;
import com.bibek.hms.services.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Appointment api healthy 📅⚡️");
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody CreateAppointmentRequestDTO requestDTO) {
        AppointmentResponseDTO appointment = appointmentService.createAppointment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDateTime") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<AppointmentResponseDTO> appointments = appointmentService.getAppointments(pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable UUID uuid) {
        return appointmentService.getAppointmentById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDoctor(@PathVariable UUID doctorId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDateTime") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDateRange(start, end, pageable);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @PathVariable UUID uuid,
            @RequestBody CreateAppointmentRequestDTO requestDTO
    ) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.updateAppointment(uuid, requestDTO);
        return ResponseEntity.ok(appointmentResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID uuid) {
        appointmentService.deleteAppointment(uuid);
        return ResponseEntity.noContent().build();
    }
}

