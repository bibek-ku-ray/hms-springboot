package com.bibek.hms.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private UUID uuid;
    private String reason;
    private LocalDateTime appointmentDateTime;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
}

