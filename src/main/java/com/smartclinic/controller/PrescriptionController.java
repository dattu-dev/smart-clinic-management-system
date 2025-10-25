package com.smartclinic.controller;

import com.smartclinic.entity.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private TokenService tokenService;
    
    // POST endpoint saves a prescription with token and request body validation
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPrescription(
            @Valid @RequestBody Prescription prescription,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validates token
        if (token == null || token.isEmpty()) {
            response.put("success", false);
            response.put("message", "Authorization token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            String email = tokenService.validateTokenAndGetEmail(token.replace("Bearer ", ""));
            Prescription savedPrescription = prescriptionService.savePrescription(prescription);
            
            // Returns structured success message using ResponseEntity
            response.put("success", true);
            response.put("message", "Prescription created successfully");
            response.put("prescription", savedPrescription);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Returns structured error message using ResponseEntity
            response.put("success", false);
            response.put("message", "Failed to create prescription: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
