package com.bibek.hms.services.impl;

import com.bibek.hms.dto.CreateDepartmentRequestDTO;
import com.bibek.hms.dto.DepartmentResponseDTO;
import com.bibek.hms.dto.DoctorResponseDTO;
import com.bibek.hms.enities.Department;
import com.bibek.hms.enities.Doctor;
import com.bibek.hms.mapper.DepartmentMapper;
import com.bibek.hms.mapper.DoctorMapper;
import com.bibek.hms.repositories.DepartmentRepository;
import com.bibek.hms.repositories.DoctorRepository;
import com.bibek.hms.services.IDepartmentService;
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
public class DepartmentServiceImpl implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public DepartmentResponseDTO createDepartment(CreateDepartmentRequestDTO requestDTO) {
        Department department = DepartmentMapper.toEntity(requestDTO);
        departmentRepository.save(department);
        return DepartmentMapper.toResponse(department);
    }

    @Override
    public List<DepartmentResponseDTO> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::toResponse).toList();
    }

    @Override
    public Page<DepartmentResponseDTO> getDepartments(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
        return departments.map(DepartmentMapper::toResponse);
    }

    @Override
    public Optional<DepartmentResponseDTO> getDepartmentById(UUID id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(DepartmentMapper::toResponse);
    }

    @Override
    public Optional<DepartmentResponseDTO> getDepartmentByName(String name) {
        Optional<Department> department = departmentRepository.findByName(name);
        return department.map(DepartmentMapper::toResponse);
    }

    @Override
    public Page<DepartmentResponseDTO> searchDepartmentsByName(String name, Pageable pageable) {
        Page<Department> departments = departmentRepository.findByNameContainingIgnoreCase(name, pageable);
        return departments.map(DepartmentMapper::toResponse);
    }

    @Override
    @Transactional
    public DepartmentResponseDTO updateDepartment(UUID id, CreateDepartmentRequestDTO requestDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setName(requestDTO.getName());
        department.setDescription(requestDTO.getDescription());

        departmentRepository.save(department);
        return DepartmentMapper.toResponse(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    @Override
    @Transactional
    public DepartmentResponseDTO addDoctorToDepartment(UUID departmentId, UUID doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        department.getDoctors().add(doctor);
        departmentRepository.save(department);

        return DepartmentMapper.toResponse(department);
    }

    @Override
    @Transactional
    public DepartmentResponseDTO removeDoctorFromDepartment(UUID departmentId, UUID doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        department.getDoctors().remove(doctor);
        departmentRepository.save(department);

        return DepartmentMapper.toResponse(department);
    }

    @Override
    public List<DoctorResponseDTO> getDoctorsByDepartment(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        return department.getDoctors().stream()
                .map(DoctorMapper::toResponse)
                .toList();
    }
}

