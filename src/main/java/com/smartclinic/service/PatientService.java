package com.smartclinic.service;

import com.smartclinic.entity.Patient;
import com.smartclinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private TokenService tokenService;
    
    public Map<String, Object> validateLogin(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (patient.getPassword().equals(password)) {
                String token = tokenService.generateToken(email);
                response.put("success", true);
                response.put("token", token);
                response.put("patient", patient);
                return response;
            }
        }
        
        response.put("success", false);
        response.put("message", "Invalid credentials");
        return response;
    }
    
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
}
