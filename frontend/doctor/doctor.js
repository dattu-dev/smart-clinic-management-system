const API_BASE_URL = 'http://localhost:8080/api';

// Login Page
if (document.getElementById('loginForm') && !document.getElementById('appointmentsList')) {
    document.getElementById('loginForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const messageDiv = document.getElementById('message');
        
        try {
            const response = await fetch(`${API_BASE_URL}/doctors/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });
            
            const data = await response.json();
            
            if (data.success) {
                localStorage.setItem('doctorToken', data.token);
                localStorage.setItem('doctorId', data.doctor.id);
                localStorage.setItem('doctorName', data.doctor.name);
                messageDiv.className = 'message success';
                messageDiv.textContent = 'Login successful! Redirecting...';
                setTimeout(() => {
                    window.location.href = 'doctor-dashboard.html';
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

// Dashboard Page - View Appointments
if (document.getElementById('appointmentsList')) {
    const token = localStorage.getItem('doctorToken');
    const doctorId = localStorage.getItem('doctorId');
    const doctorName = localStorage.getItem('doctorName');
    
    if (!token || !doctorId) {
        window.location.href = 'doctor-login.html';
    }
    
    document.getElementById('doctorName').textContent = `Welcome, ${doctorName}`;
    
    loadAppointments();
}

async function loadAppointments() {
    const doctorId = localStorage.getItem('doctorId');
    const appointmentsDiv = document.getElementById('appointmentsList');
    
    try {
        const response = await fetch(`${API_BASE_URL}/appointments/doctor/${doctorId}`);
        const appointments = await response.json();
        
        if (appointments.length === 0) {
            appointmentsDiv.innerHTML = '<p>No appointments found.</p>';
            return;
        }
        
        appointmentsDiv.innerHTML = appointments.map(appointment => `
            <div class="appointment-card">
                <h3>Patient: ${appointment.patient.name}</h3>
                <p><strong>Date & Time:</strong> ${new Date(appointment.appointmentTime).toLocaleString()}</p>
                <p><strong>Patient Email:</strong> ${appointment.patient.email}</p>
                <p><strong>Patient Phone:</strong> ${appointment.patient.phone}</p>
                <p><strong>Notes:</strong> ${appointment.notes || 'No notes'}</p>
                <span class="status ${appointment.status}">${appointment.status}</span>
            </div>
        `).join('');
    } catch (error) {
        appointmentsDiv.innerHTML = '<p>Error loading appointments.</p>';
    }
}

function logout() {
    localStorage.removeItem('doctorToken');
    localStorage.removeItem('doctorId');
    localStorage.removeItem('doctorName');
    window.location.href = 'doctor-login.html';
}
