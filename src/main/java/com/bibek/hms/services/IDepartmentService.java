package com.bibek.hms.services;

import com.bibek.hms.dto.CreateDepartmentRequestDTO;
import com.bibek.hms.dto.DepartmentResponseDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDepartmentService {
    DepartmentResponseDTO createDepartment(CreateDepartmentRequestDTO requestDTO);
    List<DepartmentResponseDTO> getDepartments();
    Page<DepartmentResponseDTO> getDepartments(Pageable pageable);
    Optional<DepartmentResponseDTO> getDepartmentById(UUID id);
    Optional<DepartmentResponseDTO> getDepartmentByName(String name);
    Page<DepartmentResponseDTO> searchDepartmentsByName(String name, Pageable pageable);
    DepartmentResponseDTO updateDepartment(UUID id, CreateDepartmentRequestDTO requestDTO);
    void deleteDepartment(UUID id);
    DepartmentResponseDTO addDoctorToDepartment(UUID departmentId, UUID doctorId);
    DepartmentResponseDTO removeDoctorFromDepartment(UUID departmentId, UUID doctorId);
    List<DoctorResponseDTO> getDoctorsByDepartment(UUID departmentId);
}

