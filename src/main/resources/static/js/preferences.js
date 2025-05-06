
document.getElementById('preferencesForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const riskTolerance = document.getElementById('riskTolerance').value;
    const timeFrame = document.getElementById('timeFrame').value;
    const frequency = document.getElementById('frequency').value;
    const investmentAmount = document.getElementById('investmentAmount').value;
    const success = document.getElementById('preferenceSuccess');

    const data = {investmentAmount, timeFrame, frequency, riskTolerance}
    
    fetch('/api/preferences', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorObj => { 
                throw new Error(errorObj.message); 
            });
        }  
        return response.json();
    })
    .then(data => {
        document.getElementById("prefAlertBox").classList.add("hidden");
        document.getElementById("prefErrorMessage").textContent = "";
        console.log("Success:", data);
        //document.getElementById('preferencesForm').reset(); 
        success.style.display = 'block';
        setTimeout(() => {
            success.style.display = 'none';
        }, 10000);
    })
    .catch(error => {
        console.error("Error:", error.message);
        const alertBox = document.getElementById("prefAlertBox");
        const errorDiv = document.getElementById("prefErrorMessage");
        alertBox.classList.remove("hidden");
        errorDiv.textContent = "Error: " + error.message;
    });
});
