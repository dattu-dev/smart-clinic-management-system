**As an** Admin
**I want to** log into the system securely
**So that** I can access the admin dashboard and manage the clinic operations

**Acceptance Criteria:**
- Admin can enter email and password
- System validates credentials against the database
- Upon successful login, admin receives a JWT token
- Admin is redirected to the dashboard
- Invalid credentials show an error message

**As an** Admin
**I want to** add new doctors to the system
**So that** patients can find and book appointments with them

**Acceptance Criteria:**
- Admin can enter doctor details (name, email, phone, speciality, available times)
- System validates all required fields
- Doctor credentials are securely stored in the database
- System shows success confirmation when doctor is added
- Admin can set doctor's available times and speciality

**As an** Admin
**I want to** view and manage all system users (doctors and patients)
**So that** I can maintain accurate records and ensure smooth clinic operations

**Acceptance Criteria:**
- Admin can view lists of all doctors and patients
- Admin can update user information
- Admin can deactivate user accounts if needed
- System maintains audit logs of admin actions

**As a** Doctor
**I want to** log into the system with my credentials
**So that** I can access my appointments and patient information

**Acceptance Criteria:**
- Doctor can enter email and password
- System validates credentials and returns JWT token
- Upon successful login, doctor is redirected to the dashboard
- Doctor can view their profile information
- Invalid credentials show an error message

**As a** Doctor
**I want to** view all my scheduled appointments
**So that** I can prepare for patient consultations and manage my schedule

**Acceptance Criteria:**
- Doctor can see a list of all appointments
- Appointments display patient name, date/time, and status
- Doctor can filter appointments by date
- System shows appointment notes if available
- Appointments are sorted chronologically

**As a** Doctor
**I want to** create and save prescriptions for my patients
**So that** patients receive proper medication instructions

**Acceptance Criteria:**
- Doctor can enter medication name, dosage, and instructions
- System validates all required prescription fields
- Prescription is linked to the patient and doctor
- System records the prescription date automatically
- Doctor receives confirmation when prescription is saved

**As a** Patient
**I want to** register and log into the system
**So that** I can book appointments and manage my healthcare

**Acceptance Criteria:**
- New patients can register with name, email, phone, date of birth, and address
- Existing patients can log in with email and password
- System validates email format and required fields
- Upon successful login, patient receives JWT token
- Patient is redirected to their dashboard

**As a** Patient
**I want to** search for doctors by name or speciality
**So that** I can find the right doctor for my medical needs

**Acceptance Criteria:**
- Patient can search doctors by name
- Patient can filter doctors by speciality
- Search results show doctor name, speciality, phone, and available times
- System displays all matching doctors
- Patient can view doctor availability

**As a** Patient
**I want to** book appointments with doctors and view my appointment history
**So that** I can schedule consultations and track my medical visits

**Acceptance Criteria:**
- Patient can select a doctor and preferred time slot
- System validates appointment availability
- Patient can add notes to appointment request
- System confirms appointment booking
- Patient can view all their past and upcoming appointments
- Appointments display doctor name, speciality, date/time, and status
