document.addEventListener("DOMContentLoaded", () => {
    fetch("/api/user", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        document.getElementById("username").textContent = data.username;
        document.getElementById("email").textContent = data.email;
    })
    .catch(error => {
        console.error("Error:", error);
    });
});
