package com.bibek.hms.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private UUID uuid;
    private String name;
    private String specialization;
    private String email;
}
