-- SQL Schema for Smart Clinic Management System

-- Create Doctor Table
CREATE TABLE Doctor (
    DoctorID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Specialization VARCHAR(100),
    Phone VARCHAR(15)
);

-- Create Patient Table
CREATE TABLE Patient (
    PatientID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DateOfBirth DATE,
    Phone VARCHAR(15)
);

-- Create Appointment Table
CREATE TABLE Appointment (
    AppointmentID INT PRIMARY KEY,
    DoctorID INT,
    PatientID INT,
    AppointmentDate DATETIME,
    Status VARCHAR(20),
    FOREIGN KEY (DoctorID) REFERENCES Doctor(DoctorID),
    FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

-- Create Prescription Table
CREATE TABLE Prescription (
    PrescriptionID INT PRIMARY KEY,
    AppointmentID INT,
    Medication VARCHAR(100),
    Dosage VARCHAR(50),
    FOREIGN KEY (AppointmentID) REFERENCES Appointment(AppointmentID)
);

-- Create Admin Table
CREATE TABLE Admin (
    AdminID INT PRIMARY KEY,
    Username VARCHAR(50),
    Password VARCHAR(50)
);

-- Stored Procedure to Get Daily Appointment Report By Doctor
CREATE PROCEDURE GetDailyAppointmentReportByDoctor
    @DoctorID INT,
    @AppointmentDate DATETIME
AS
BEGIN
    SELECT * FROM Appointment
    WHERE DoctorID = @DoctorID AND CAST(AppointmentDate AS DATE) = CAST(@AppointmentDate AS DATE);
END;

-- Stored Procedure to Get Doctor with Most Patients By Month
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth
    @Month INT,
    @Year INT
AS
BEGIN
    SELECT DoctorID, COUNT(PatientID) AS PatientCount
    FROM Appointment
    WHERE MONTH(AppointmentDate) = @Month AND YEAR(AppointmentDate) = @Year
    GROUP BY DoctorID
    ORDER BY PatientCount DESC
    LIMIT 1;
END;

-- Stored Procedure to Get Doctor with Most Patients By Year
CREATE PROCEDURE GetDoctorWithMostPatientsByYear
    @Year INT
AS
BEGIN
    SELECT DoctorID, COUNT(PatientID) AS PatientCount
    FROM Appointment
    WHERE YEAR(AppointmentDate) = @Year
    GROUP BY DoctorID
    ORDER BY PatientCount DESC
    LIMIT 1;
END;