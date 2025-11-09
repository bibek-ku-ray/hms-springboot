package com.bibek.hms.enities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurances")
public class Insurance extends Base{
    @Column(unique = true, nullable = false)
    private String policyNumber;

    @Column(nullable = false)
    private String providerName;

    private String validUntil;

    @OneToOne(mappedBy = "insurance", fetch = FetchType.LAZY)
    private Patient patient;
}
