# MySQL Database Design Documentation

## ER Diagram Description
The database design consists of the following entities: Doctor, Patient, Appointment, Prescription, and Admin. Each entity has specific attributes, and relationships between entities are established through foreign keys.

### Tables and Fields

1. **Doctor Table**  
   - `id`: INT, Primary Key  
   - `name`: VARCHAR(100)  
   - `email`: VARCHAR(100), UNIQUE  
   - `phone`: VARCHAR(15)  
   - `speciality`: VARCHAR(100)  
   - `password`: VARCHAR(255)  
   - `available_times`: TEXT  

2. **Patient Table**  
   - `id`: INT, Primary Key  
   - `name`: VARCHAR(100)  
   - `email`: VARCHAR(100), UNIQUE  
   - `phone`: VARCHAR(15)  
   - `password`: VARCHAR(255)  
   - `date_of_birth`: DATE  
   - `address`: TEXT  

3. **Appointment Table**  
   - `id`: INT, Primary Key  
   - `doctor_id`: INT, Foreign Key references Doctor(id)  
   - `patient_id`: INT, Foreign Key references Patient(id)  
   - `appointment_time`: DATETIME  
   - `status`: ENUM('scheduled', 'completed', 'canceled')  
   - `notes`: TEXT  

4. **Prescription Table**  
   - `id`: INT, Primary Key  
   - `doctor_id`: INT, Foreign Key references Doctor(id)  
   - `patient_id`: INT, Foreign Key references Patient(id)  
   - `medication`: VARCHAR(100)  
   - `dosage`: VARCHAR(100)  
   - `instructions`: TEXT  
   - `prescribed_date`: DATE  

5. **Admin Table**  
   - `id`: INT, Primary Key  
   - `name`: VARCHAR(100)  
   - `email`: VARCHAR(100), UNIQUE  
   - `password`: VARCHAR(255)  

## Relationship Constraints
- Each **Doctor** can have multiple **Appointments**.  
- Each **Patient** can have multiple **Appointments**.  
- Each **Appointment** is associated with one **Doctor** and one **Patient**.  
- Each **Prescription** is associated with one **Doctor** and one **Patient**.  

This schema ensures that all relationships are maintained through foreign keys, providing referential integrity across the database.