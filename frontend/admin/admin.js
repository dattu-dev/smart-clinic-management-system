const API_BASE_URL = 'http://localhost:8080/api';

// Login Page
if (document.getElementById('loginForm') && !document.getElementById('addDoctorForm')) {
    document.getElementById('loginForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const messageDiv = document.getElementById('message');
        
        try {
            const response = await fetch(`${API_BASE_URL}/admin/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });
            
            const data = await response.json();
            
            if (data.success) {
                localStorage.setItem('adminToken', data.token);
                localStorage.setItem('adminEmail', email);
                messageDiv.className = 'message success';
                messageDiv.textContent = 'Login successful! Redirecting...';
                setTimeout(() => {
                    window.location.href = 'admin-dashboard.html';
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

// Dashboard Page - Add Doctor
if (document.getElementById('addDoctorForm')) {
    const token = localStorage.getItem('adminToken');
    
    if (!token) {
        window.location.href = 'admin-login.html';
    }
    
    document.getElementById('addDoctorForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            speciality: document.getElementById('speciality').value,
            password: document.getElementById('password').value,
            availableTimes: document.getElementById('availableTimes').value
        };
        
        const messageDiv = document.getElementById('message');
        
        try {
            const response = await fetch(`${API_BASE_URL}/admin/add-doctor`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(formData)
            });
            
            const data = await response.json();
            
            if (data.success) {
                messageDiv.className = 'message success';
                messageDiv.textContent = 'Doctor added successfully!';
                document.getElementById('addDoctorForm').reset();
            } else {
                messageDiv.className = 'message error';
                messageDiv.textContent = data.message || 'Failed to add doctor';
            }
        } catch (error) {
            messageDiv.className = 'message error';
            messageDiv.textContent = 'Error adding doctor. Please try again.';
        }
    });
}

function logout() {
    localStorage.removeItem('adminToken');
    localStorage.removeItem('adminEmail');
    window.location.href = 'admin-login.html';
}
