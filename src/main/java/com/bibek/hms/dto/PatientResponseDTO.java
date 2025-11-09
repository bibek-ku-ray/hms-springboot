package com.bibek.hms.dto;

import com.bibek.hms.enums.BloodType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private UUID uuid;
    private String name;
    private LocalDate dob;
    private String email;
    private String gender;
    private BloodType bloodType;
    private UUID doctorId;
    private String doctorName;
    private UUID insuranceId;
}

