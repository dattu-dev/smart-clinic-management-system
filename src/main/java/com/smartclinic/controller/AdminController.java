package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.service.AdminService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return ResponseEntity.ok(adminService.validateLogin(email, password));
    }
    
    @PostMapping("/add-doctor")
    public ResponseEntity<Map<String, Object>> addDoctor(
            @RequestBody Doctor doctor,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (token == null || token.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            tokenService.validateTokenAndGetEmail(token.replace("Bearer ", ""));
            Doctor savedDoctor = adminService.addDoctor(doctor);
            
            response.put("success", true);
            response.put("message", "Doctor added successfully");
            response.put("doctor", savedDoctor);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to add doctor");
            return ResponseEntity.status(500).body(response);
        }
    }
}
