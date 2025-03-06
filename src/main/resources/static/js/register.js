// Javascript register.html

document.getElementById("submit").addEventListener('click', () => {

    debugger;
        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('passwordcon').value;

        if (password !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }

        const data = { username, email, password };

        fetch('/api/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Registration failed');
            }
            return response.json();
        })
        .then(data => {
            alert("Registration successful!");
            window.location.href = "/login.html";
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Registration failed. Please try again.");
        });
    });