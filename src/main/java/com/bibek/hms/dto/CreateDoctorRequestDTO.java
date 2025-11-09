package com.bibek.hms.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorRequestDTO {
    private String name;
    private String specialization;
    private String email;
}
