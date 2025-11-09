package com.bibek.hms.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResponseDTO {
    private UUID uuid;
    private String policyNumber;
    private String providerName;
    private String validUntil;
    private UUID patientId;
    private String patientName;
}

