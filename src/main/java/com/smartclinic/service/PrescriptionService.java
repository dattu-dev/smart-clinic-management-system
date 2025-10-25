package com.smartclinic.service;

import com.smartclinic.entity.Prescription;
import com.smartclinic.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    public Prescription savePrescription(Prescription prescription) {
        prescription.setPrescribedDate(LocalDateTime.now());
        return prescriptionRepository.save(prescription);
    }
}
