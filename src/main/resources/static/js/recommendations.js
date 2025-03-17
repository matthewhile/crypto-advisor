const recommendedCryptos = ["bitcoin", "ethereum"];
const charts = {};

async function fetchCurrentMarketData(symbol) {
    try {
        let response = await fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${symbol}&order=market_cap_desc&per_page=1&page=1`);
        if (!response.ok) return null;
        let data = await response.json();
        return data.length > 0 ? data[0] : null;
    } catch (error) {
        console.error(`Error fetching market data for ${symbol}:`, error);
        return null;
    }
}

async function fetchCryptoHistory(symbol) {
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


function renderChart(canvas, data, symbol) {
    let ctx = canvas.getContext('2d');

    if (!data || !data.prices || data.prices.length === 0) {
        canvas.parentElement.innerHTML += `<p style='color:red;'>No chart data available.</p>`;
        return;
    }

    // Downsample: Take every 7th data point (one per week)
    let filteredData = data.prices.filter((_, index) => index % 7 === 0);

    let labels = filteredData.map(entry => {
        let date = new Date(entry[0]); // Convert timestamp to date
        return date.toLocaleDateString(); // Format as MM/DD/YYYY
    });

    let prices = filteredData.map(entry => entry[1]); // Extract price values

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
                borderWidth: 3, // Thicker lines for clarity
                tension: 0.3, // Smooth curve
                pointRadius: 3, // Small points to show data without clutter
                fill: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    title: { display: true, text: 'Date' },
                    ticks: { maxTicksLimit: 10 } // Reduce x-axis labels
                },
                y: {
                    title: { display: true, text: 'Price (USD)' }
                }
            }
        }
    });
}


async function renderRecommendedCrypto() {
    let container = document.getElementById("cryptoContainer");
    container.innerHTML = "";
    
    for (let symbol of recommendedCryptos) {
        let [marketData, historyData] = await Promise.all([
            fetchCurrentMarketData(symbol),
            fetchCryptoHistory(symbol)
        ]);

        if (!marketData || !historyData) continue;

        let homepageLink = marketData.links?.homepage?.[0] || '#';

        let card = document.createElement("div");
        card.className = "card crypto-card";
        card.innerHTML = `
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5 class="card-title">${marketData.name} (${marketData.symbol.toUpperCase()})</h5>
                        <p class="card-text">Price: $${marketData.current_price.toLocaleString()}</p>
                        <p class="card-text">Market Cap: $${marketData.market_cap.toLocaleString()}</p>
                        <p class="card-text">Volume: $${marketData.total_volume.toLocaleString()}</p>
                        <p class="card-text">Website: <a href="${homepageLink}" target="_blank" rel="noopener noreferrer">${marketData.name} Official</a></p>
                    </div>
                    <div class="col-md-6">
                        <canvas class="crypto-chart" id="chart-${symbol}"></canvas>
                    </div>
                </div>
            </div>
        `;
        container.appendChild(card);

        setTimeout(() => renderChart(document.getElementById(`chart-${symbol}`), historyData, symbol), 100);
    }
}

document.addEventListener("DOMContentLoaded", renderRecommendedCrypto);
