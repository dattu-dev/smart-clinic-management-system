package com.smartclinic.service;

import com.smartclinic.entity.Admin;
import com.smartclinic.entity.Doctor;
import com.smartclinic.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private TokenService tokenService;
    
    public Doctor addDoctor(Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }
    
    public Map<String, Object> validateLogin(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                String token = tokenService.generateToken(email);
                response.put("success", true);
                response.put("token", token);
                response.put("admin", admin);
                return response;
            }
        }
        
        response.put("success", false);
        response.put("message", "Invalid credentials");
        return response;
    }
}
