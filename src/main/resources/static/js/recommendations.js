const recommendedCryptos = ["bitcoin", "ethereum"];
const charts = {};

document.addEventListener("DOMContentLoaded", function () {
    loadSavedRecommendations();
});

// Load user's saved recommendations
function loadSavedRecommendations() {
    fetch("http://localhost:8080/api/recommendations/load")
        .then(response => response.json())
        .then(data => {
            debugger;
            const tableBody = document.getElementById("savedRecommendationTableBody");
            //tableBody.innerHTML = "";

            data.forEach(recommendation => {
                addRecommendationRow(recommendation, tableBody, false);
            });
            
            attachEventListener();
        })
        .catch(error => console.error("Error fetching saved recommendations:", error));
}

document.getElementById('recommendationsBtn').addEventListener('click', function() {
    fetch("/api/recommendations", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorMessage => { 
                throw new Error(errorMessage); 
            });
        }
        return response.json();
    })
    .then(data => {
        // Clear error if request succeeds
        document.getElementById("recErrorMessage").textContent = "";
        const cryptoContainer = document.getElementById("cryptoContainer");
        const topMatches = data.topMatches;
        const prefs = data.userPreferences;
        const explanation = data.explanation

        topMatches.forEach(item => {
            const crypto = item.crypto;
            const score = item.score;

            let cryptoCard = document.createElement("div");
            cryptoCard.className = "card crypto-card";
            cryptoCard.innerHTML = `
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="card-title"> <img src="${crypto.image}" alt="${crypto.name}" style="width:30px; height:30px;">
                            ${crypto.name} (${crypto.symbol.toUpperCase()})</h5>
                            <p class="card-text">Price: $${crypto.current_price.toLocaleString()}</p>
                            <p class="card-text">Market Cap: $${crypto.market_cap.toLocaleString()}</p>
                            <p class="card-text">Volume: $${crypto.total_volume.toLocaleString()}</p>
                        </div>
                        <div>
                            <button class="save-crypto-btn" type="button" data-id="${crypto.symbol}" data-name="${crypto.name}">Save</button>
                        </div>
                    </div>
                </div>
            `;
            cryptoContainer.appendChild(cryptoCard);
        });
         // Save individual recommendation when save button is clicked
         document.querySelectorAll(".save-crypto-btn").forEach(button => {
            button.addEventListener("click", function(event) {
                event.preventDefault();
        
                const symbolId = event.target.getAttribute("data-id");
                const symbolName = event.target.getAttribute("data-name");
        
                const data = {symbolId, symbolName};
        
                fetch("api/recommendations/save", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(data)
                })
                .then(response => response.json())
                .then(recommendation => {
                    const tableBody = document.getElementById("savedRecommendationTableBody");
                    addRecommendationRow(recommendation, tableBody); 
                    console.log("Saved Recommendation!");
                    attachEventListener();
                })
                .catch(error => {
                    console.error("Save failed:", error);
                });
        
            });
        });
        const cryptoExplanation = document.getElementById("cryptoExplanation")
        let explainationCard = document.createElement("div");
        explainationCard.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <p class="explanation-title">Why are these cryptocurrencies recommended?</p>
                    <div style="font-weight: bold">Risk Tolerance - ${prefs.riskTolerance} <br>
                        Time Frame - ${prefs.timeFrame} <br>
                        Investment Frequency - ${prefs.frequency}<br>
                        Investment Amount - $${prefs.investmentAmount.toLocaleString()}<br><br>
                    </div>
                    <div>
                        ${explanation}
                    </div>
                </div>
            </div> `
        cryptoExplanation.appendChild(explainationCard);
    })
    .catch(error => {
        console.error("Error:", error);
        const alertBox = document.getElementById("recAlertBox");
        const errorDiv = document.getElementById("recErrorMessage");
        alertBox.classList.remove("hidden");
        errorDiv.textContent = "Error: " + error.message;
    });
});

// Add a new expense row to the table
function addRecommendationRow(recommendation, tableBody) {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${recommendation.symbolId.toUpperCase()}</td>
        <td>${recommendation.symbolName}</td>
        <td>${recommendation.dateSaved}</td>
        <td class="delete-recommendation">
            <button class="btn delete-btn" data-id="${recommendation.id}">
                <i class="fa-regular fa-trash-can"></i>
            </button>
        </td>   
    `;
    tableBody.appendChild(row);
}

// Delete button click event listener
function attachEventListener() {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", deleteRecommendation);
    });
}

// Delete a saved recommendation 
function deleteRecommendation() {
    const recommendationId = this.getAttribute("data-id");  

    fetch(`/api/recommendations/delete/${recommendationId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            this.closest('tr').remove();
        } 
    })
    .catch(error => {
        console.error("Error:", error);
        alert("There was an error deleting the recommendation");
    });
};




// async function renderRecommendedCrypto() {
//     let container = document.getElementById("cryptoContainer");
//     container.innerHTML = "";
    
//     for (let symbol of recommendedCryptos) {
//         let [marketData, historyData] = await Promise.all([
//             fetchCurrentMarketData(symbol),
//             fetchCryptoHistory(symbol)
//         ]);

//         if (!marketData || !historyData) continue;

//         let homepageLink = marketData.links?.homepage?.[0] || '#';

//         let card = document.createElement("div");
//         card.className = "card crypto-card";
//         card.innerHTML = `
//             <div class="card-body">
//                 <div class="row">
//                     <div class="col-md-6">
//                         <h5 class="card-title">${marketData.name} (${marketData.symbol.toUpperCase()})</h5>
//                         <p class="card-text">Price: $${marketData.current_price.toLocaleString()}</p>
//                         <p class="card-text">Market Cap: $${marketData.market_cap.toLocaleString()}</p>
//                         <p class="card-text">Volume: $${marketData.total_volume.toLocaleString()}</p>
//                         <p class="card-text">Website: <a href="${homepageLink}" target="_blank" rel="noopener noreferrer">${marketData.name} Official</a></p>
//                     </div>
//                     <div class="col-md-6">
//                         <canvas class="crypto-chart" id="chart-${symbol}"></canvas>
//                     </div>
//                 </div>
//             </div>
//         `;
//         container.appendChild(card);

//         setTimeout(() => renderChart(document.getElementById(`chart-${symbol}`), historyData, symbol), 100);
//     }
// }

// async function fetchCurrentMarketData(symbol) {
//     try {
//         let response = await fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${symbol}&order=market_cap_desc&per_page=1&page=1`);
//         if (!response.ok) return null;
//         let data = await response.json();
//         return data.length > 0 ? data[0] : null;
//     } catch (error) {
//         console.error(`Error fetching market data for ${symbol}:`, error);
//         return null;
//     }
// }

// async function fetchCryptoHistory(symbol) {
//     try {
//         let response = await fetch(`https://api.coingecko.com/api/v3/coins/${symbol}/market_chart?vs_currency=usd&days=180&interval=daily`);
//         if (!response.ok) return null;
//         let data = await response.json();
//         return data;
//     } catch (error) {
//         console.error(`Error fetching historical data for ${symbol}:`, error);
//         return null;
//     }
// }


// function renderChart(canvas, data, symbol) {
//     let ctx = canvas.getContext('2d');

//     if (!data || !data.prices || data.prices.length === 0) {
//         canvas.parentElement.innerHTML += `<p style='color:red;'>No chart data available.</p>`;
//         return;
//     }

//     // Downsample: Take every 7th data point (one per week)
//     let filteredData = data.prices.filter((_, index) => index % 7 === 0);

//     let labels = filteredData.map(entry => {
//         let date = new Date(entry[0]); // Convert timestamp to date
//         return date.toLocaleDateString(); // Format as MM/DD/YYYY
//     });

//     let prices = filteredData.map(entry => entry[1]); // Extract price values

//     let color = prices[prices.length - 1] >= prices[0] ? 'green' : 'red';

//     if (charts[symbol]) charts[symbol].destroy();
//     charts[symbol] = new Chart(ctx, {
//         type: 'line',
//         data: {
//             labels: labels,
//             datasets: [{
//                 label: `${symbol.toUpperCase()} Price Trend (Last 6 Months)`,
//                 data: prices,
//                 borderColor: color,
//                 borderWidth: 3, // Thicker lines for clarity
//                 tension: 0.3, // Smooth curve
//                 pointRadius: 3, // Small points to show data without clutter
//                 fill: false
//             }]
//         },
//         options: {
//             responsive: true,
//             maintainAspectRatio: false,
//             scales: {
//                 x: {
//                     title: { display: true, text: 'Date' },
//                     ticks: { maxTicksLimit: 10 } // Reduce x-axis labels
//                 },
//                 y: {
//                     title: { display: true, text: 'Price (USD)' }
//                 }
//             }
//         }
//     });
// }


// document.addEventListener("DOMContentLoaded", renderRecommendedCrypto);
