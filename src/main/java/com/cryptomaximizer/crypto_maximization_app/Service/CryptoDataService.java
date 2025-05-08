package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Exception.DataNotFoundException;
import com.cryptomaximizer.crypto_maximization_app.Exception.NoCryptoDataAvailableException;
import com.cryptomaximizer.crypto_maximization_app.Model.ChartDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CryptoDataService {
    private final RestTemplate restTemplate;

    @Value("${coingecko.api.key}") // Load API key from application.properties
    private String apiKey;

    @Value("${crypto.default.symbols}") // Load desired list of crypto symbols from application.properties
    private String defaultSymbols;

    public CryptoDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Retrieve the market data for each crypto passed into CoinGecko API
    @Cacheable("marketDataDTO")
    public List<MarketDataDTO> getMarketData() {
        List<String> symbols = Arrays.asList(defaultSymbols.split(","));
        if (symbols == null) {
            throw new DataNotFoundException("No crypto symbols found to pass to CoinGecko API");
        }

        String symbolList = String.join(",", symbols);

        String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("ids", symbolList)
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", symbols.size())
                .queryParam("page", 1)
                .queryParam("x_cg_demo_api_key", apiKey)
                .toUriString();

        try {
            MarketDataDTO[] response = restTemplate.getForObject(url, MarketDataDTO[].class);

            if (response == null || response.length == 0) {
                throw new NoCryptoDataAvailableException("No (market) data returned from CoinGecko API");
            }

            return Arrays.asList(response);

        } catch (Exception e) {
            throw new NoCryptoDataAvailableException("An error occurred fetching (market) data from CoinGecko API");
        }
    }

    // Retrieve the chart data for each crypto passed into CoinGecko API
    @Cacheable("chartDataDTO")
    public Map<String, ChartDataDTO> getMarketChart() {
        Map<String, ChartDataDTO> chartDataMap = new HashMap<>();

        List<String> symbols = Arrays.asList(defaultSymbols.split(","));
        if (symbols == null) {
            throw new DataNotFoundException("No crypto symbols found to pass to CoinGecko API");
        }

        for (String symbol : symbols) {
            String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/" + symbol + "/market_chart")
                    .queryParam("vs_currency", "usd")
                    .queryParam("days", 180)
                    .queryParam("interval", "daily")
                    .queryParam("x_cg_demo_api_key", apiKey)
                    .toUriString();

            try {
                ChartDataDTO chartData = restTemplate.getForObject(url, ChartDataDTO.class);
                chartDataMap.put(symbol, chartData);

                if (chartData == null) {
                    throw new NoCryptoDataAvailableException("No (chart) data returned from CoinGecko API");
                }

            } catch (Exception e) {
                //throw new NoCryptoDataAvailableException("Error fetching (chart) data from CoinGecko API: " + e.getMessage());
                throw new NoCryptoDataAvailableException("An error occurred fetching (chart) data from CoinGecko API");
            }
        }
        return chartDataMap;
    }
}
