package com.cryptomaximizer.crypto_maximization_app.Service;

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
public class CryptoService {
    private final RestTemplate restTemplate;

    @Value("${coingecko.api.key}") // Load API key from application.properties
    private String apiKey;

    @Value("${crypto.default.symbols}") // Load desired list of crypto symbols from application.properties
    private String defaultSymbols;

    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("marketDataDTO")
    public List<MarketDataDTO> getMarketData() {
        List<String> symbols = Arrays.asList(defaultSymbols.split(","));
        String symbolList = String.join(",", symbols);

        String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("ids", symbolList)
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", symbols.size())
                .queryParam("page", 1)
                .queryParam("x_cg_demo_api_key", apiKey)
                .toUriString();

        MarketDataDTO[] response = restTemplate.getForObject(url, MarketDataDTO[].class);
        return Arrays.asList(response);
    }
    @Cacheable("chartDataDTO")
    public Map<String, ChartDataDTO> getMarketChart() {
        Map<String, ChartDataDTO> chartDataMap = new HashMap<>();

        List<String> symbols = Arrays.asList(defaultSymbols.split(","));

        for (String symbol : symbols) {
            String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/" + symbol + "/market_chart")
                    .queryParam("vs_currency", "usd")
                    .queryParam("days", 180)
                    .queryParam("interval", "daily")
                    .queryParam("x_cg_demo_api_key", apiKey)
                    .toUriString();

            ChartDataDTO chartData = restTemplate.getForObject(url, ChartDataDTO.class);
            chartDataMap.put(symbol, chartData);
        }

        return chartDataMap;
    }
}
