package com.cryptomaximizer.crypto_maximization_app.Service;


import com.cryptomaximizer.crypto_maximization_app.Model.CryptoDailyPrice;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Repository.CryptoDailyPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CryptoDataSchedulerService {

    private final CryptoDailyPriceRepository cryptoDailyPriceRepository;
    private final CryptoDataService cryptoDataService;

    public CryptoDataSchedulerService(CryptoDailyPriceRepository cryptoDailyPriceRepository, CryptoDataService cryptoDataService) {
        this.cryptoDailyPriceRepository = cryptoDailyPriceRepository;
        this.cryptoDataService = cryptoDataService;
    }


    @Scheduled(cron = "0 0 22 * * *") // Run once a day at 10 PM server time
    public void saveDailyCryptoPrices() {
        List<MarketDataDTO> marketDataList = cryptoDataService.getMarketData();

        LocalDate today = LocalDate.now();

        for (MarketDataDTO crypto : marketDataList) {
            // Check if today's price already exists
            boolean alreadySaved = cryptoDailyPriceRepository.existsBySymbolAndDate(crypto.getSymbol(), today);

            if (!alreadySaved) {
                CryptoDailyPrice dailyPrice = new CryptoDailyPrice();
                dailyPrice.setSymbol(crypto.getSymbol());
                dailyPrice.setDate(today);
                dailyPrice.setHigh24h(crypto.getHigh24h());
                dailyPrice.setLow24h(crypto.getLow24h());

                cryptoDailyPriceRepository.save(dailyPrice);
            }
        }
        System.out.println("Crypto price data for " + today + " saved successfully!");
    }
}
