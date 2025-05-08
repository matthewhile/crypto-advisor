package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Exception.CryptoCalculationException;
import com.cryptomaximizer.crypto_maximization_app.Exception.NoCryptoDataAvailableException;
import com.cryptomaximizer.crypto_maximization_app.Model.CryptoDailyPrice;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import com.cryptomaximizer.crypto_maximization_app.Repository.CryptoDailyPriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CryptoScoringService {

    private final CryptoDailyPriceRepository cryptoDailyPriceRepository;

    public CryptoScoringService(CryptoDailyPriceRepository cryptoDailyPriceRepository) {
        this.cryptoDailyPriceRepository = cryptoDailyPriceRepository;
    }

    public List<ScoredCryptoDTO> calculateCryptoScore(List<MarketDataDTO> marketDataList) {
        if (marketDataList == null) {
            throw new NoCryptoDataAvailableException("An error occurred fetching (market) data from CoinGecko API");
        }

        List<ScoredCryptoDTO> scoredCryptos = new ArrayList<>();
        Map<String, Double> volatilityMap = getVolatilityScore(marketDataList);
        // Store the min and max prices for
        double[] minMax = findMinMaxVolatility(volatilityMap.values());
        double minVolatility = minMax[0];
        double maxVolatility = minMax[1];

        double maxMarketCap = getMaxMarketCap(marketDataList);
        double minVolume = getMinVolume(marketDataList);
        double maxVolume = getMaxVolume(marketDataList);

        for (MarketDataDTO crypto : marketDataList) {
            double marketCapScore = getMarketCapScore(crypto, maxMarketCap);
            double volumeScore = getVolumeScore(crypto, maxVolume, minVolume);

            double rawVolatility = volatilityMap.get(crypto.getSymbol());
            double normalizedVolatility;
            if (maxVolatility != minVolatility) {
                double volatilityRange = maxVolatility - minVolatility;
                double relativeVolatility = (rawVolatility - minVolatility) / volatilityRange;
                normalizedVolatility = Math.sqrt(relativeVolatility);
            } else {
                normalizedVolatility = 0.5; // fallback value
            }

            // Invert normalizedVolatility, so it can be associated with user preference scores
            double volatilityScore = 1 - normalizedVolatility;
            System.out.println("normalized volatility for " + crypto.getName() + " is " + volatilityScore);

            // Sum scores for market cap, volume, and volatility + apply weights to calculate a final crypto score
            double totalScore = 0.4 * marketCapScore + 0.3 * volumeScore + 0.3 * volatilityScore;
            System.out.println("---------------------------------------------------------");
            System.out.println("total score for " + crypto.getName() + " is " + totalScore);
            System.out.println("---------------------------------------------------------");

            if (totalScore < 0) {
                throw new CryptoCalculationException("An error occurred calculating crypto scores");
            }
            scoredCryptos.add(new ScoredCryptoDTO(crypto, totalScore));

        }

        return scoredCryptos;
    }

    // Calculate and return normalized market cap score for each crypto
    private double getMarketCapScore(MarketDataDTO crypto, double maxMarketCap) {
        double marketCap = crypto.getMarketCap();
        double normalizedMarketCap = marketCap / maxMarketCap;
        System.out.println("normalized market cap for " + crypto.getName() + " is " + normalizedMarketCap);

        return normalizedMarketCap;
    }

    // Calculate and return normalized volume score for each crypto
    private double getVolumeScore(MarketDataDTO crypto, double maxVolume, double minVolume) {
        double volume = crypto.getTotalVolume();
        double normalizedVolume = (volume - minVolume) / (maxVolume - minVolume);
        System.out.println("normalized volume for " + crypto.getName() + " is " + normalizedVolume);

        return normalizedVolume;
    }

    // Map each crypto’s symbol to its raw volatility score.
    private Map<String, Double> getVolatilityScore(List<MarketDataDTO> list) {
        Map<String, Double> volatilityMap = new HashMap<>();

        for (MarketDataDTO crypto : list) {
            double totalVolatility = computeTotalVolatility(crypto);
            //System.out.println("Total volatility for " + crypto.getName() + " is " + totalVolatility);
            volatilityMap.put(crypto.getSymbol(), totalVolatility);
        }

        return volatilityMap;
    }

    // Computes the average daily historical volatility combined with today's high/low range and percent change.
    public double computeTotalVolatility(MarketDataDTO crypto) {
        if (crypto == null) {
            throw new CryptoCalculationException("Error occurred calculating volatility score");
        }
        int numberOfDays = 7; // Number of days to use
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(numberOfDays);

        List<CryptoDailyPrice> historicalPrices = cryptoDailyPriceRepository.findBySymbolAndDateBetween(
                crypto.getSymbol(), startDate, endDate);

        double historicalVolatilitySum = 0.0;
        for (CryptoDailyPrice price : historicalPrices) {
            historicalVolatilitySum += (price.getHigh24h() - price.getLow24h());
        }
        double historicalAverageVolatility = historicalPrices.isEmpty() ? 0.0 : historicalVolatilitySum / historicalPrices.size();

        double priceChangeVolatilityToday = crypto.getHigh24h() - crypto.getLow24h();
        double percentChangeVolatilityToday = Math.abs(crypto.getPriceChangePercentage24h());

        return 0.7 * historicalAverageVolatility + 0.2 * priceChangeVolatilityToday + 0.1 * percentChangeVolatilityToday;
    }

    private double[] findMinMaxVolatility(Collection<Double> values) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double value : values) {
            if (value < min) min = value;
            if (value > max) max = value;
        }

        return new double[]{min, max};
    }

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

}

