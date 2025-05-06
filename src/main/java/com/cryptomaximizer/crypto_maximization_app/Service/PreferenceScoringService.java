package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredPreferenceDTO;
import org.springframework.stereotype.Service;

@Service
public class PreferenceScoringService {

    public ScoredPreferenceDTO calculatePreferenceScore(Preference preference) {
        // Weights are likely to change throughout development / testing process
        double riskScore = scoreRiskTolerance(preference.getRiskTolerance());
        System.out.println("User risk score is " + riskScore);
        double frequencyScore = scoreFrequency(preference.getFrequency());
        System.out.println("User frequency score is " + frequencyScore);
        double timeFrameScore = scoreTimeFrame(preference.getTimeFrame());
        System.out.println("User time frame score is " + timeFrameScore);
        double amountScore = scoreInvestmentAmount(preference.getInvestmentAmount());
        System.out.println("User amount score is " + amountScore);

        double total = riskScore * 0.4 + frequencyScore * 0.2 + timeFrameScore * 0.3 + amountScore * 0.1;
        System.out.println("User total preference score is " + total);

        return new ScoredPreferenceDTO(riskScore, frequencyScore, timeFrameScore, amountScore, total);
    }

    public double scoreRiskTolerance(String riskTolerance) {
        if (riskTolerance == null) {
            throw new IllegalArgumentException("Risk tolerance cannot be null.");
        }

        switch (riskTolerance.toLowerCase()) {
            case "low":
                return 0.9;
            case "medium":
                return 0.5;
            case "high":
                return 0.2;
            default:
                throw new IllegalArgumentException("Invalid risk tolerance value: " + riskTolerance);
        }
    }


    public double scoreFrequency(String frequency) {
        /* Weights for frequency are determined based on the idea that a user investing (daily, weekly)
        * aligns with more liquid or volatile assets while a user investing (monthly, yearly, one-time)
        * aligns with lower risk and less volatile assets. */
        if (frequency == null) {
            throw new IllegalArgumentException("Frequency cannot be null.");
        }
        switch(frequency.toLowerCase()) {
            case "daily":
                return 0.0;
            case "weekly":
                return 0.2;
            case "monthly":
                return 0.4;
            case "yearly":
                return 0.7;
            case "one-time investment":
                return 0.8;
            default:
                throw new IllegalArgumentException("Invalid frequency value: " + frequency);
        }
    }

    public double scoreTimeFrame(String timeFrame) {
        // Shorter terms are weighted closer to 0, longer terms are weighted closer to 1
        if (timeFrame == null) {
            throw new IllegalArgumentException("Time frame cannot be null.");
        }
        switch(timeFrame.toLowerCase()) {
            case "short-term":
                return 0.2;
            case "medium-term":
                return 0.5;
            case "long-term":
                return 0.8;
            default:
                throw new IllegalArgumentException("Invalid time frame value: " + timeFrame);
        }
    }

    public double scoreInvestmentAmount(double amount) {
        // Lower amounts are weighted closer to 1, higher amounts are weighted closer to 0
        if (amount < 1) {
            throw new IllegalArgumentException("Investment amount must be a positive number.");
        }
        if (amount < 500) return 0.8;
        else if (amount < 2000) return 0.6;
        else if (amount < 10000) return 0.4;
        else if (amount < 50000) return 0.2;
        else return 0.0;
    }

    public String generateExplanation(ScoredPreferenceDTO scoredPreference, Preference preference) {
        double total = scoredPreference.getTotalScore();
        double amount = preference.getInvestmentAmount();
        int roundedAmount = (int) Math.round(amount);
        String disclaimer = "To create this recommendation, we assigned numeric weights to each of your preferences and summed them to create a preference score. " +
                "Your preference score was compared to scored cryptocurrencies based on the market cap, volume, and volatility of each crypto. " +
                "The 3 crypto scores matching closest to your total preference score are then recommended." +
                " This is not financial advice and it's important to do your own research before investing in cryptocurrency.";
        String preferenceDetails = "These cryptocurrencies are recommended because of your " + preference.getRiskTolerance().toLowerCase() + " risk tolerance, " +
                preference.getTimeFrame().toLowerCase() + " investment goals, and desired investment amount of $" + roundedAmount + " on a " +
                preference.getFrequency().toLowerCase() + " basis.";
        String explanation = "";

        if (0.75 < total) {
            explanation = preferenceDetails + " We’ve matched you with cryptocurrencies known for stability and strong market caps." + disclaimer;
        } else if (0.45 <= total && total <= 0.75) {
            explanation = preferenceDetails + " These recommendations are tailored to offer a mix of volatility and potential performance." + disclaimer;
        } else {
            explanation = preferenceDetails + " These cryptocurrencies offer higher volatility and the potential for rapid movement." + disclaimer;
        }
        return explanation;
    }
}

