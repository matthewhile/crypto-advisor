package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoScoringService {

    public List<ScoredCryptoDTO> calculateCryptoScore(List<MarketDataDTO> marketDataList) {
        List<ScoredCryptoDTO> scoredCryptos = new ArrayList<>();

        // The highest market cap in marketDataList
        double maxMarketCap = getMaxMarketCap(marketDataList);

        // The lowest volume in marketDataList
        double minVolume = getMinVolume(marketDataList);

        // The highest volume in marketDataList
        double maxVolume = getMaxVolume(marketDataList);

        // The highest and lowest volatility values in marketDataList
        double[] volatilityRange = getVolatilityRange(marketDataList);
        double minVolatility = volatilityRange[0];
        double maxVolatility = volatilityRange[1];

        for (MarketDataDTO crypto : marketDataList) {
            double marketCapScore = getMarketCapScore(crypto, maxMarketCap);
            double volumeScore = getVolumeScore(crypto, maxVolume, minVolume);
            double volatilityScore = getVolatilityScore(crypto, minVolatility, maxVolatility);

            // Example formula: total score is weighted sum of all individual scores
            double totalScore = marketCapScore * 0.3 + volumeScore * 0.3 + volatilityScore * 0.4;
            System.out.println("Total score for " + crypto.getName() + " = " + totalScore);


            scoredCryptos.add(new ScoredCryptoDTO(crypto, totalScore));
        }

        return scoredCryptos;
    }

    // Calculate and return normalized market cap score for each crypto
    private double getMarketCapScore(MarketDataDTO crypto, double maxMarketCap) {
        double marketCap = crypto.getMarketCap();
        double normalizedMarketCap = marketCap / maxMarketCap;
        //System.out.println("normalized market cap for " + crypto.getName() + " is " + normalizedMarketCap);

        return normalizedMarketCap;
    }

    // Calculate and return normalized volume score for each crypto
    private double getVolumeScore(MarketDataDTO crypto, double maxVolume, double minVolume) {
        double volume = crypto.getTotalVolume();
        double normalizedVolume = (volume - minVolume) / (maxVolume - minVolume);
        //System.out.println("normalized volume for " + crypto.getName() + " is " + normalizedVolume);

        return normalizedVolume;
    }

    // Calculate and return normalized volatility score for each crypto
    private double getVolatilityScore(MarketDataDTO crypto, double minVolatility, double maxVolatility) {
        double priceChangeVolatility = (crypto.getHigh24h() - crypto.getLow24h());
        double percentChangeVolatility = Math.abs(crypto.getPriceChangePercentage24h());
        double totalVolatility = 0.4 * priceChangeVolatility + 0.6 * percentChangeVolatility;
        // Smooth and normalize the volatility score
        double normalizedVolatility = Math.sqrt((totalVolatility - minVolatility) / (maxVolatility - minVolatility));
        //System.out.println("normalized volatility for " + crypto.getName() + " is " + normalizedVolatility);

        // Invert the volatility score by subtracting from 1, so that high volatility --> lower score & low volatility --> higher score
        return 1 - normalizedVolatility;
    }

    // TODO: write functions to calculate historical performance scores (maybe).
//    private double getHistoricalPerformanceScore(MarketDataDTO crypto) {
//        // Calculate and return historical performance score
//    }

    private double getMaxMarketCap (List<MarketDataDTO> marketDataList) {
        double maxMarketCap = marketDataList.stream()
                .mapToDouble(MarketDataDTO::getMarketCap)
                .max()
                .orElse(1); // Default to 1 to avoid division by zero
        return maxMarketCap;
    }

    private double getMinVolume(List<MarketDataDTO> marketDataList) {
        double minVolume = marketDataList.stream()
                .mapToDouble(MarketDataDTO::getTotalVolume)
                .min()
                .orElse(1);
        return minVolume;
    }

    private double getMaxVolume(List<MarketDataDTO> marketDataList) {
        double maxVolume = marketDataList.stream()
                .mapToDouble(MarketDataDTO::getTotalVolume)
                .max()
                .orElse(1);
        return maxVolume;
    }

    public double[] getVolatilityRange(List<MarketDataDTO> cryptos) {
        double minVolatility = Double.MAX_VALUE;
        double maxVolatility = Double.MIN_VALUE;

        for (MarketDataDTO crypto : cryptos) {
            double priceChangeVolatility = (crypto.getHigh24h() - crypto.getLow24h());
            double percentChangeVolatility = Math.abs(crypto.getPriceChangePercentage24h());
            double totalVolatility = 0.5 * priceChangeVolatility + 0.5 * percentChangeVolatility;

            if (totalVolatility < minVolatility) {
                minVolatility = totalVolatility;
            }
            if (totalVolatility > maxVolatility) {
                maxVolatility = totalVolatility;
            }
        }

        return new double[]{minVolatility, maxVolatility};
    }
}

