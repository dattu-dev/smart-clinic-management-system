package com.smartclinic.controller;

import com.smartclinic.entity.Appointment;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookAppointment(
            @RequestBody Appointment appointment,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (token == null || token.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            tokenService.validateTokenAndGetEmail(token.replace("Bearer ", ""));
            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
            
            response.put("success", true);
            response.put("appointment", savedAppointment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to book appointment");
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientAppointments(
            @PathVariable Long patientId,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (token == null || token.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            tokenService.validateTokenAndGetEmail(token.replace("Bearer ", ""));
            List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
            
            response.put("success", true);
            response.put("appointments", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid token");
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }
}
