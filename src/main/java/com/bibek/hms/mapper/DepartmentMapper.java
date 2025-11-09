package com.bibek.hms.mapper;

import com.bibek.hms.dto.CreateDepartmentRequestDTO;
import com.bibek.hms.dto.DepartmentResponseDTO;
import com.bibek.hms.enities.Department;

public class DepartmentMapper {
    public static Department toEntity(CreateDepartmentRequestDTO dto) {
        return Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static DepartmentResponseDTO toResponse(Department department) {
        return DepartmentResponseDTO.builder()
                .uuid(department.getUuid())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}

