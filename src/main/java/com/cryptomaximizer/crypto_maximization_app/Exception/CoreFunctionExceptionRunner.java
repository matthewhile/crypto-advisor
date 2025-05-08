package com.cryptomaximizer.crypto_maximization_app.Exception;

import com.cryptomaximizer.crypto_maximization_app.Model.*;
import com.cryptomaximizer.crypto_maximization_app.Repository.ExpenseRepository;
import com.cryptomaximizer.crypto_maximization_app.Repository.IncomeRepository;
import com.cryptomaximizer.crypto_maximization_app.Repository.RecommendationRepository;
import com.cryptomaximizer.crypto_maximization_app.Repository.UserRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

// Test exceptions for core functions
@Profile("exception-test")
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
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CryptoScoringService cryptoScoringService;

    // Intentionally trigger exceptions to test core functions
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
            double score = preferenceScoringService.scoreRiskTolerance(null);
            System.out.println("Score: " + score);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught IllegalArgumentException: " + e.getMessage());
        }

        try {
            User test_user = userRepository.findById(502L).orElseThrow();
            preferenceDataService.getPreferencesForUser(test_user);
        } catch (DataNotFoundException e) {
            System.err.println("Caught DataNotFoundException: " + e.getMessage());
        }

        try {
            User test_user = userRepository.findById(502L).orElseThrow(); // use valid ID
            incomeService.getTaxInfoForUser(test_user);
        } catch (DataNotFoundException e) {
            System.err.println("Caught DataNotFoundException: " + e.getMessage());
        }

        try {
            cryptoScoringService.calculateCryptoScore(null);
        } catch (NoCryptoDataAvailableException e) {
            System.err.println("Caught NoCryptoDataAvailableException: " + e.getMessage());
        }

        try {
            Income income = incomeRepository.findById(1L).orElseThrow();
            incomeService.fetchIncomeTaxCalculation(income);
        } catch (Exception e) {
            System.err.println("Caught TaxCalculationException: " + e.getMessage());
        }

        try {
            Expense expense = new Expense();
            expenseRepository.save(expense);
        } catch (Exception e) {
            System.err.println("Caught Expense SaveDataException: " + e.getMessage());
        }

        try {
            Recommendation savedRecommendation = new Recommendation();
            recommendationRepository.save(savedRecommendation);
        } catch (Exception e) {
            System.err.println("Caught Recommendation SaveDataException: " + e.getMessage());
        }

        try {
            cryptoScoringService.computeTotalVolatility(null);
        } catch (CryptoCalculationException e) {
            System.err.println("Caught CryptoCalculationException: " + e.getMessage());
        }

        try {
            double score = preferenceScoringService.scoreInvestmentAmount(-1);
            System.out.println("Score: " + score);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught IllegalArgumentException: " + e.getMessage());
        }



    }
}

