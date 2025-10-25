package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private TokenService tokenService;
    
    // Exposes a GET endpoint for doctor availability using dynamic parameters
    @GetMapping("/availability")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @RequestParam String speciality,
            @RequestParam String date,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validates token
        if (token == null || token.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            String email = tokenService.validateTokenAndGetEmail(token.replace("Bearer ", ""));
            List<Doctor> doctors = doctorService.getDoctorsBySpeciality(speciality);
            
            response.put("success", true);
            response.put("doctors", doctors);
            response.put("date", date);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid token");
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam String name) {
        return ResponseEntity.ok(doctorService.searchDoctorsByName(name));
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return ResponseEntity.ok(doctorService.validateLogin(email, password));
    }
}
