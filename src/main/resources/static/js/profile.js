document.addEventListener("DOMContentLoaded", () => {
   loadUserInfo();
   loadUserPreferences();
   loadIncomeInfo();
});

function loadUserInfo() {
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
}

function loadUserPreferences() {
    fetch("/api/preferences", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        document.getElementById("investmentAmount").textContent = data.investmentAmount;
        document.getElementById("timeFrame").textContent = data.timeFrame;
        document.getElementById("frequency").textContent = data.frequency;
        document.getElementById("riskTolerance").textContent = data.riskTolerance;
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

function loadIncomeInfo() {
    fetch("/api/income", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        document.getElementById("state").textContent = data.state;
        document.getElementById("filingStatus").textContent = data.filingStatus;
        document.getElementById("grossIncome").textContent = data.grossIncome;
    })
    .catch(error => {
        console.error("Error:", error);
    });
}