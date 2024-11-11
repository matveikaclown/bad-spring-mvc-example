package ru.ssau.springlab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    private MedicalHistory medicalHistory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    @ManyToMany(mappedBy = "pets")
    private Set<Owner> owners;
}
