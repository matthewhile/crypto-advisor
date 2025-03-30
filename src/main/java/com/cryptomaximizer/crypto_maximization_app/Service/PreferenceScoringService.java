package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredPreferenceDTO;
import org.springframework.stereotype.Service;

@Service
public class PreferenceScoringService {

    public ScoredPreferenceDTO calculateUserScore(Preference preference) {
        // Weights are likely to change throughout development / testing process
        double riskScore = scoreRiskTolerance(preference.getRiskTolerance());
        System.out.println("User risk score is " + riskScore);
        double frequencyScore = scoreFrequency(preference.getFrequency());
        System.out.println("User frequency score is " + frequencyScore);
        double timeFrameScore = scoreTimeFrame(preference.getTimeFrame());
        System.out.println("User time frame score is " + timeFrameScore);
        double amountScore = scoreInvestmentAmount(preference.getInvestmentAmount());
        System.out.println("User amount score is " + amountScore);

        double total = riskScore + frequencyScore + timeFrameScore + amountScore;
        System.out.println("User total preference score is " + total);

        return new ScoredPreferenceDTO(riskScore, frequencyScore, timeFrameScore, amountScore, total);
    }

    private double scoreRiskTolerance(String riskTolerance) {
        // Lower tolerances are weighted closer to 0, higher tolerances are weighted closer to 1
        switch(riskTolerance.toLowerCase()) {
            case "low":
                 return 0.2;
            case "medium":
                 return 0.5;
            case "high":
                 return 1.0;
            default:
                return 0.5;
        }
    }

    private double scoreFrequency(String frequency) {
        /* Weights for frequency are determined based on the assumption that a user investing (daily, weekly)
        * aligns with more liquid or volatile assets while a user investing (monthly, yearly, one-time)
        * aligns with lower risk and less volatile assets. */
        switch(frequency.toLowerCase()) {
            case "daily":
                return 1.0;
            case "weekly":
                return 0.8;
            case "monthly":
                return 0.6;
            case "yearly":
                return 0.3;
            case "one-time investment":
                return 0.2;
            default:
                return 0.5;
        }
    }

    private double scoreTimeFrame(String timeFrame) {
        // Shorter terms are weighted closer to 0, longer terms are weighted closer to 1
        switch(timeFrame.toLowerCase()) {
            case "short-term":
                return 0.2;
            case "medium-term":
                return 0.5;
            case "long-term":
                return 1.0;
            default:
                return 0.5;
        }
    }

    private double scoreInvestmentAmount(double amount) {
        // Lower amounts are weighted closer to 0, higher amounts are weighted closer to 1
        if (amount < 500) return 0.2;
        else if (amount < 2000) return 0.4;
        else if (amount < 10000) return 0.6;
        else if (amount < 50000) return 0.8;
        else return 1.0;
    }

}

