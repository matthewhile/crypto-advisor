function fetchChartData() {
    fetch("http://localhost:8080/api/crypto/chart", {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        
    })
    .catch(error => {
        console.error("Error:", error);
    });
};


// const topCryptos = ["bitcoin", "ethereum", "litecoin", "avalanche", "usdc"];
const topCryptos = ["bitcoin", "litecoin"];

        const userCrypto = "solana, ripple"; //WILL BE VARIABLE DEPENDENT ON USER INPUT
        const charts = {}; 

        async function fetchCryptoHistory(symbol) {
            debugger;
            try {
                let response = await fetch(`https://api.coingecko.com/api/v3/coins/${symbol}/market_chart?vs_currency=usd&days=180&interval=daily`);
                if (!response.ok) return null;
                let data = await response.json();
                return data;
            } catch (error) {
                console.error(`Error fetching historical data for ${symbol}:`, error);
                return null;
            }
        }

        // function handlePreferences() {
        //     let userPrefs = localStorage.getItem("cryptoPreferences");
        //     if (!userPrefs) {
        //         window.location.href = "preferences.html";
        //     } else {
        //         alert("Your preferences are already saved!");
        //     }
        // }

        // function handleBudgetCalculator() {
        //     window.location.href = "income.html"
        // }

        // function handleRecommendations() {
        //     window.location.href = "recommendations.html"
        // }

        // function handleProfile() {
        //     window.location.href = "profile.html"
        // }

        function renderChart(container, data, symbol) {
    let canvas = document.createElement('canvas');
    canvas.id = `chart-${symbol}`;
    container.appendChild(canvas);
    let ctx = canvas.getContext('2d');

    if (!data || !data.prices || data.prices.length === 0) {
        container.innerHTML += `<p style='color:red;'>No chart data available.</p>`;
        return;
    }

    let filteredData = data.prices.filter((_, index) => index % 7 === 0);
    let labels = filteredData.map(entry => new Date(entry[0]).toLocaleDateString());
    let prices = filteredData.map(entry => entry[1]);
    let color = prices[prices.length - 1] >= prices[0] ? 'green' : 'red';

    if (charts[symbol]) charts[symbol].destroy();
    charts[symbol] = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: `${symbol.toUpperCase()} Price Trend (Last 6 Months)`,
                data: prices,
                borderColor: color,
                borderWidth: 3,
                tension: 0.3,
                pointRadius: 3,
                fill: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                x: {
                    title: { display: true, text: 'Date' },
                    ticks: { maxTicksLimit: 10 }
                },
                y: {
                    title: { display: true, text: 'Price (USD)' }
                }
            }
        }
    });
}

async function renderCryptoTrends(containerId, cryptos) {
    let container = document.getElementById(containerId);
    if (!container) {
        console.error(`Container ${containerId} not found!`);
        return;
    }

    if (!Array.isArray(cryptos)) {
        cryptos = [cryptos];
    }

    let cryptoBoxes = container.querySelectorAll(".crypto-box");
    cryptoBoxes.forEach(box => box.remove());

    for (let symbol of cryptos) {
        if (typeof symbol !== "string") {
            console.error(`Invalid symbol type:`, symbol);
            continue;
        }

        let cryptoData = await fetchCryptoHistory(symbol);
        if (!cryptoData) {
            console.log(`No data for ${symbol}`);
            continue;
        }

        let box = document.createElement('div');
        box.className = "crypto-box";
        box.innerHTML = `<h3>${symbol.toUpperCase()}</h3>`;

        container.appendChild(box);
        renderChart(box, cryptoData, symbol);
    }
}

renderCryptoTrends("dailyMoversBox", topCryptos);
renderCryptoTrends("userCrypto", userCrypto.split(", "));
