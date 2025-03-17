document.getElementById('preferencesForm').addEventListener('submit', function() {
    const riskTolerance = document.getElementById('riskTolerance').value;
    const timeHeld = document.getElementById('timeHeld').value;
    const investmentAmount = document.getElementById('investmentAmount').value;
});

document.addEventListener("DOMContentLoaded", function() {
    loadTimeFrameDropdown();
    loadFrequencyDropdown();
    loadRiskToleranceDropdown();

});


function loadTimeFrameDropdown() {		

    const timeFrames = [
        {value: 1, name: "Less than 1 week"}, 
        {value: 2, name: "1 to 6 months"}, 
        {value: 3, name: "6 months to 1 year"}, 
        {value: 4, name: "1 to 5 years"}, 
        {value: 5, name: "5+ years"}, 
    ];

    const timeFrameDropdown = document.getElementById("timeFrame");

    timeFrames.forEach(frame => {
        let option = document.createElement("option");
        option.value = frame.value;
        option.textContent = frame.name;
        timeFrameDropdown.appendChild(option);
    });
};

function loadFrequencyDropdown() {		

    const frequencies = [
        {value: 1, name: "Daily"}, 
        {value: 2, name: "Weekly"}, 
        {value: 3, name: "Monthly"}, 
        {value: 4, name: "Yearly"}, 
        {value: 5, name: "One-time Investment"}, 
    ];

    const frequenciesDropdown = document.getElementById("frequency");

    frequencies.forEach(frequency => {
        let option = document.createElement("option");
        option.value = frequency.value;
        option.textContent = frequency.name;
        frequenciesDropdown.appendChild(option);
    });
};

function loadRiskToleranceDropdown() {		

    const riskLevels = [
        {value: 1, name: "Low"}, 
        {value: 2, name: "Medium"}, 
        {value: 3, name: "High"}, 
    ];

    const riskLevelsDropdown = document.getElementById("riskTolerance");

    riskLevels.forEach(riskLevel => {
        let option = document.createElement("option");
        option.value = riskLevel.value;
        option.textContent = riskLevel.name;
        riskLevelsDropdown.appendChild(option);
    });
};