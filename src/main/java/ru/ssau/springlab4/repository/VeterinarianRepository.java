package ru.ssau.springlab4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.springlab4.model.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
