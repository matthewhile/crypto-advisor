const advertisedCryptos = ["bitcoin", "ethereum", "dogecoin", "tether", "usd-coin"];
const charts = {};

async function fetchCryptoData(symbol) {
    try {
        let response = await fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${symbol}&order=market_cap_desc&per_page=1&page=1&sparkline=true`);
        if (!response.ok) return null;
        let data = await response.json();
        return data.length > 0 ? data[0] : null;
    } catch (error) {
        console.error(`Error fetching data for ${symbol}:`, error);
        return null;
    }
}

function renderChart(canvas, data, symbol) {
    let ctx = canvas.getContext('2d');
    let prices = data.sparkline_in_7d?.price || [];
    if (prices.length === 0) {
        canvas.parentElement.innerHTML += `<p style='color:red;'>No chart data available.</p>`;
        return;
    }
    let color = data.price_change_percentage_24h >= 0 ? 'green' : 'red';
    if (charts[symbol]) charts[symbol].destroy();
    charts[symbol] = new Chart(ctx, {
        type: 'line',
        data: {
            labels: Array.from({ length: prices.length }, (_, i) => i + 1),
            datasets: [{ label: `${data.name} Price Trend`, data: prices, borderColor: color, borderWidth: 2, fill: false }]
        },
        options: { responsive: true, maintainAspectRatio: false }
    });
}

async function renderCryptoAdvertisements() {
    let container = document.getElementById("cryptoContainer");
    container.innerHTML = "";
    
    for (let symbol of advertisedCryptos) {
        let cryptoData = await fetchCryptoData(symbol);
        if (!cryptoData) continue;
        
        let homepageLink = cryptoData.links?.homepage?.[0] || '#';
        
        let card = document.createElement("div");
        card.className = "card crypto-card";
        card.innerHTML = `
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5 class="card-title">${cryptoData.name} (${cryptoData.symbol.toUpperCase()})</h5>
                        <p class="card-text">Price: $${cryptoData.current_price.toLocaleString()}</p>
                        <p class="card-text">Market Cap: $${cryptoData.market_cap.toLocaleString()}</p>
                        <p class="card-text">Volume: $${cryptoData.total_volume.toLocaleString()}</p>
                        <p class="card-text">Website: <a href="${homepageLink}" target="_blank" rel="noopener noreferrer">${cryptoData.name} Official</a></p>
                    </div>
                    <div class="col-md-6">
                        <canvas class="crypto-chart" id="chart-${symbol}"></canvas>
                    </div>
                </div>
            </div>
        `;
        container.appendChild(card);
        
        setTimeout(() => renderChart(document.getElementById(`chart-${symbol}`), cryptoData, symbol), 100);
    }
}

document.addEventListener("DOMContentLoaded", renderCryptoAdvertisements);
