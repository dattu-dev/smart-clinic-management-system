const API_BASE_URL = 'http://localhost:8080/api';

// Login Page
if (document.getElementById('loginForm') && !document.getElementById('searchInput')) {
    document.getElementById('loginForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const messageDiv = document.getElementById('message');
        
        try {
            const response = await fetch(`${API_BASE_URL}/patients/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });
            
            const data = await response.json();
            
            if (data.success) {
                localStorage.setItem('patientToken', data.token);
                localStorage.setItem('patientId', data.patient.id);
                localStorage.setItem('patientName', data.patient.name);
                messageDiv.className = 'message success';
                messageDiv.textContent = 'Login successful! Redirecting...';
                setTimeout(() => {
                    window.location.href = 'patient-dashboard.html';
                }, 1000);
            } else {
                messageDiv.className = 'message error';
                messageDiv.textContent = data.message || 'Invalid credentials';
            }
        } catch (error) {
            messageDiv.className = 'message error';
            messageDiv.textContent = 'Login failed. Please try again.';
        }
    });
}

// Dashboard Page
if (document.getElementById('searchInput')) {
    const token = localStorage.getItem('patientToken');
    const patientId = localStorage.getItem('patientId');
    const patientName = localStorage.getItem('patientName');
    
    if (!token || !patientId) {
        window.location.href = 'patient-login.html';
    }
    
    document.getElementById('patientName').textContent = `Welcome, ${patientName}`;
    
    loadMyAppointments();
}

async function searchDoctors() {
    const searchInput = document.getElementById('searchInput').value;
    const resultsDiv = document.getElementById('searchResults');
    
    if (!searchInput) {
        resultsDiv.innerHTML = '<p>Please enter a doctor name to search.</p>';
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/doctors/search?name=${searchInput}`);
        const doctors = await response.json();
        
        if (doctors.length === 0) {
            resultsDiv.innerHTML = '<p>No doctors found.</p>';
            return;
        }
        
        resultsDiv.innerHTML = doctors.map(doctor => `
            <div class="doctor-card">
                <h3>${doctor.name}</h3>
                <p><strong>Speciality:</strong> ${doctor.speciality}</p>
                <p><strong>Email:</strong> ${doctor.email}</p>
                <p><strong>Phone:</strong> ${doctor.phone}</p>
                <p><strong>Available Times:</strong> ${doctor.availableTimes}</p>
            </div>
        `).join('');
    } catch (error) {
        resultsDiv.innerHTML = '<p>Error searching doctors.</p>';
    }
}

async function loadMyAppointments() {
    const patientId = localStorage.getItem('patientId');
    const token = localStorage.getItem('patientToken');
    const appointmentsDiv = document.getElementById('myAppointments');
    
    try {
        const response = await fetch(`${API_BASE_URL}/appointments/patient/${patientId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        const data = await response.json();
        const appointments = data.appointments;
        
        if (!appointments || appointments.length === 0) {
            appointmentsDiv.innerHTML = '<p>No appointments found.</p>';
            return;
        }
        
        appointmentsDiv.innerHTML = appointments.map(appointment => `
            <div class="appointment-card">
                <h3>Dr. ${appointment.doctor.name}</h3>
                <p><strong>Speciality:</strong> ${appointment.doctor.speciality}</p>
                <p><strong>Date & Time:</strong> ${new Date(appointment.appointmentTime).toLocaleString()}</p>
                <p><strong>Notes:</strong> ${appointment.notes || 'No notes'}</p>
                <span class="status ${appointment.status}">${appointment.status}</span>
            </div>
        `).join('');
    } catch (error) {
        appointmentsDiv.innerHTML = '<p>Error loading appointments.</p>';
    }
}

function logout() {
    localStorage.removeItem('patientToken');
    localStorage.removeItem('patientId');
    localStorage.removeItem('patientName');
    window.location.href = 'patient-login.html';
}
