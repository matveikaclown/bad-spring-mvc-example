package ru.ssau.springlab4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.springlab4.model.MedicalHistory;
import ru.ssau.springlab4.repository.MedicalHistoryRepository;


import java.util.List;

@Service
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryRepository.findAll();
    }

    public MedicalHistory getMedicalHistoryById(Long id) {
        return medicalHistoryRepository.findById(id).orElse(null);
    }

    public void addMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistoryRepository.save(medicalHistory);
    }
}
