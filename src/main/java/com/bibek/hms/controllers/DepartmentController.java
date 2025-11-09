package com.bibek.hms.controllers;

import com.bibek.hms.dto.CreateDepartmentRequestDTO;
import com.bibek.hms.dto.DepartmentResponseDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.services.IDepartmentService;
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
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Department api healthy 🏢⚡️");
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody CreateDepartmentRequestDTO requestDTO) {
        DepartmentResponseDTO department = departmentService.createDepartment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<DepartmentResponseDTO> departments = departmentService.getDepartments(pageable);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable UUID uuid) {
        return departmentService.getDepartmentById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DepartmentResponseDTO>> searchDepartmentsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<DepartmentResponseDTO> departments = departmentService.searchDepartmentsByName(name, pageable);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorResponseDTO>> getDoctorsByDepartment(@PathVariable UUID id) {
        List<DoctorResponseDTO> doctors = departmentService.getDoctorsByDepartment(id);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/{id}/doctors/{doctorId}")
    public ResponseEntity<DepartmentResponseDTO> addDoctorToDepartment(
            @PathVariable UUID id,
            @PathVariable UUID doctorId
    ) {
        DepartmentResponseDTO department = departmentService.addDoctorToDepartment(id, doctorId);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}/doctors/{doctorId}")
    public ResponseEntity<DepartmentResponseDTO> removeDoctorFromDepartment(
            @PathVariable UUID id,
            @PathVariable UUID doctorId
    ) {
        DepartmentResponseDTO department = departmentService.removeDoctorFromDepartment(id, doctorId);
        return ResponseEntity.ok(department);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(
            @PathVariable UUID uuid,
            @RequestBody CreateDepartmentRequestDTO requestDTO
    ) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.updateDepartment(uuid, requestDTO);
        return ResponseEntity.ok(departmentResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID uuid) {
        departmentService.deleteDepartment(uuid);
        return ResponseEntity.noContent().build();
    }
}

