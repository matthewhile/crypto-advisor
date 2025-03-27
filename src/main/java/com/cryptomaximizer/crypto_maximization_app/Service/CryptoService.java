package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.ChartDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.List;

@Service
public class CryptoService {
    private final RestTemplate restTemplate;

    @Value("${coingecko.api.key}") // Load API key from application.properties
    private String apiKey;

    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MarketDataDTO> getMarketData(String symbol) {
        String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("ids", symbol)
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", 1)
                .queryParam("page", 1)
                .queryParam("x_cg_demo_api_key", apiKey)
                .toUriString();

        MarketDataDTO[] response = restTemplate.getForObject(url, MarketDataDTO[].class);
        return Arrays.asList(response);
    }

    public ChartDataDTO getMarketChart(String symbol) {
        String url = UriComponentsBuilder.fromUriString("https://api.coingecko.com/api/v3/coins/" + symbol + "/market_chart")
                .queryParam("vs_currency", "usd")
                .queryParam("days", 180)
                .queryParam("interval", "daily")
                .queryParam("x_cg_demo_api_key", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, ChartDataDTO.class);
    }
}
