package com.bibek.hms.enities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor  extends Base{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @Column(unique = true)
    private String email;

    @OneToMany(
            mappedBy = "doctor",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(
            mappedBy = "doctor",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(mappedBy = "doctors", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Department> departments = new HashSet<>();
}
