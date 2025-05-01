package com.cryptomaximizer.crypto_maximization_app.Exception;

import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.UserRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoDataService;
import com.cryptomaximizer.crypto_maximization_app.Service.IncomeService;
import com.cryptomaximizer.crypto_maximization_app.Service.PreferenceDataService;
import com.cryptomaximizer.crypto_maximization_app.Service.PreferenceScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoreFunctionExceptionRunner implements CommandLineRunner {

    @Autowired
    private CryptoDataService cryptoDataService;
    @Autowired
    private PreferenceScoringService preferenceScoringService;
    @Autowired
    private PreferenceDataService preferenceDataService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncomeService incomeService;

    @Override
    public void run(String... args) {
        try {
            System.out.println("Calling core function with invalid parameters...");
            List<MarketDataDTO> result = cryptoDataService.getMarketData();
            System.out.println("Result: " + result);
        } catch (NoCryptoDataAvailableException e) {
            System.err.println("Caught NoCryptoDataAvailableException: " + e.getMessage());
        }

        try {
            double score = preferenceScoringService.scoreRiskTolerance(null); // Should throw IllegalArgumentException or custom
            System.out.println("Score: " + score);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught IllegalArgumentException: " + e.getMessage());
        }

//        try {
//            User test_user = userRepository.findById(502L).orElseThrow(); // use valid ID
//            preferenceDataService.getPreferencesForUser(test_user);
//        } catch (DataNotFoundException e) {
//            System.err.println("Caught DataNotFoundException: " + e.getMessage());
//        }
//
//        try {
//            User test_user = userRepository.findById(502L).orElseThrow(); // use valid ID
//            incomeService.getTaxInfoForUser(test_user);
//        } catch (DataNotFoundException e) {
//            System.err.println("Caught DataNotFoundException: " + e.getMessage());
//        }

    }
}

