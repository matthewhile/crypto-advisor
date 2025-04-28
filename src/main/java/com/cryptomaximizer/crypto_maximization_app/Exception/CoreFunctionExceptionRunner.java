package com.cryptomaximizer.crypto_maximization_app.Exception;

import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoreFunctionExceptionRunner implements CommandLineRunner {

    @Autowired
    private CryptoDataService cryptoDataService;

    @Override
    public void run(String... args) {
        try {
//            System.out.println("Calling core function with invalid parameters...");
//            List<MarketDataDTO> result = cryptoDataService.getMarketData();
//            System.out.println("Result: " + result);
        } catch (NoCryptoDataAvailableException e) {
//            System.err.println("Caught NoCryptoDataAvailableException: " + e.getMessage());
        }
    }
}

