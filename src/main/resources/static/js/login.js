// Javascript for login.html

 document.getElementById("loginForm").addEventListener("submit", function(event) {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            fetch("/login", {  
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: new URLSearchParams({
                    username: username,
                    password: password
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Login failed");
                }
                return response.text();
            })
            .then(() => {
                window.location.href = "/index.html";
            })
            .catch(error => {
                alert(error.message);
            });
        });