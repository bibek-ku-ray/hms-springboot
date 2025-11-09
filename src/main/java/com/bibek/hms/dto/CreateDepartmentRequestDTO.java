package com.bibek.hms.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequestDTO {
    private String name;
    private String description;
}

