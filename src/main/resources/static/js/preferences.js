
document.getElementById('preferencesForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const riskTolerance = document.getElementById('riskTolerance').value;
    const timeFrame = document.getElementById('timeFrame').value;
    const frequency = document.getElementById('frequency').value;
    const investmentAmount = document.getElementById('investmentAmount').value;
    const success = document.getElementById('preferenceSuccess');
    const error = document.getElementById('preferenceError');

    const data = {investmentAmount, timeFrame, frequency, riskTolerance}
    
    fetch('/api/preferences', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        console.log("Success:", data);
        document.getElementById('preferencesForm').reset(); 
        success.style.display = 'block';
    })
    .catch(error => {
        console.error("Error:", error);
        error.style.display = 'block';
    });
});
