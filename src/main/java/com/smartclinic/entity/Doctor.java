package com.smartclinic.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(unique = true)
    private String email;
    
    private String phone;
    private String speciality;
    private String password;
    
    @Column(name = "available_times", columnDefinition = "TEXT")
    private String availableTimes;
    
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    // Constructors
    public Doctor() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getAvailableTimes() { return availableTimes; }
    public void setAvailableTimes(String availableTimes) { this.availableTimes = availableTimes; }
    
    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}
