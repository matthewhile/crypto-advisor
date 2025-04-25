// Javascript for login.html

 document.getElementById("loginForm").addEventListener("submit", function(event) {
            event.preventDefault();
            debugger;

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;
            const loginError = document.getElementById("loginError");

            console.log(loginError); // Should log the <p> element

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
                    throw new Error();
                }
                return response.text();
            })
            .then(() => {
                window.location.href = "/index.html";
            })
            .catch(() => {
                loginError.style.display = "block";
            });
        });