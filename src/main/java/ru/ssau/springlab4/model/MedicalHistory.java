package ru.ssau.springlab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicalHistory;
    private String firstVisitDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
