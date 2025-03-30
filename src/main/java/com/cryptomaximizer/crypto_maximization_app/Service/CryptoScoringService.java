package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoScoringService {

    public List<ScoredCryptoDTO> calculateTotalScore(List<MarketDataDTO> marketDataList) {
        List<ScoredCryptoDTO> scoredCryptos = new ArrayList<>();

        // Calculate the highest market cap in marketDataList
        double maxMarketCap = marketDataList.stream()
                .mapToDouble(MarketDataDTO::getMarketCap)
                .max()
                .orElse(1); // Default to 1 to avoid division by zero

        // Calculate the highest total volume in marketDataList
        double maxVolume = marketDataList.stream()
                .mapToDouble(MarketDataDTO::getTotalVolume)
                .max()
                .orElse(1); // Default to 1 to avoid division by zero

        for (MarketDataDTO crypto : marketDataList) {
            double marketCapScore = getMarketCapScore(crypto, maxMarketCap);
            double volumeScore = getVolumeScore(crypto, maxVolume);
//            double volatilityScore = getVolatilityScore(crypto);
//            double historicalPerformanceScore = getHistoricalPerformanceScore(crypto);

            // Example formula: total score is weighted sum of all individual scores
//            double totalScore = marketCapScore * 0.3 + volumeScore * 0.2 +
//                    volatilityScore * 0.3 + historicalPerformanceScore * 0.2;
//
//            scoredCryptos.add(new ScoredCryptoDTO(crypto, totalScore));
        }

        return scoredCryptos;
    }

    private double getMarketCapScore(MarketDataDTO crypto, double maxMarketCap) {
        // Calculate and return market cap score
        double marketCap = crypto.getMarketCap();
        double normalizedMarketCap = marketCap / maxMarketCap;
        System.out.println("normalized market cap for " + crypto.getName() + " is " + normalizedMarketCap);

        return normalizedMarketCap;
    }

    private double getVolumeScore(MarketDataDTO crypto, double maxVolume) {
        // Calculate and return volume score
        double volume = crypto.getTotalVolume();

        double logMaxVolume = Math.log(maxVolume);
        double logVolume = Math.log(volume);
        double normalizedVolume = logVolume / logMaxVolume;
        System.out.println("normalized volume for " + crypto.getName() + " is " + normalizedVolume);

        return normalizedVolume;
    }
// TODO: write functions to calculate normalized volatility and performance scores
    // need to use ChartDataDTO in addition to MarketDataDTO?

//    private double getVolatilityScore(MarketDataDTO crypto) {
//        // Calculate and return volatility score
//    }

//    private double getHistoricalPerformanceScore(MarketDataDTO crypto) {
//        // Calculate and return historical performance score
//    }
}

