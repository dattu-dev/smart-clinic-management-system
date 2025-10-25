package com.smartclinic.repository;

import com.smartclinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByEmail(String email);
    
    List<Doctor> findBySpeciality(String speciality);
    
    List<Doctor> findByNameContaining(String name);
}
