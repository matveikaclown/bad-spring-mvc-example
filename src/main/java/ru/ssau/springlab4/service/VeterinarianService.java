package ru.ssau.springlab4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.springlab4.model.Veterinarian;
import ru.ssau.springlab4.repository.VeterinarianRepository;

import java.util.List;

@Service
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;

    @Autowired
    public VeterinarianService(VeterinarianRepository veterinarianRepository) {
        this.veterinarianRepository = veterinarianRepository;
    }

    public List<Veterinarian> getAllVeterinarians() {
        return veterinarianRepository.findAll();
    }

    public Veterinarian getVeterinarianById(Long id) {
        return veterinarianRepository.findById(id).orElse(null);
    }

    public void addVeterinarian(Veterinarian veterinarian) {
        veterinarianRepository.save(veterinarian);
    }
}