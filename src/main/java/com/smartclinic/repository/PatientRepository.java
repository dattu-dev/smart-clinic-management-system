package com.smartclinic.repository;

import com.smartclinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Method retrieves patient by email using derived query
    Optional<Patient> findByEmail(String email);
    
    // Method retrieves patient using either email or phone number
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
