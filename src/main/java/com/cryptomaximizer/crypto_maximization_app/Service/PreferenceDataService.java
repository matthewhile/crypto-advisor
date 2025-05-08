package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Exception.DataNotFoundException;
import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceDataService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    // Save new preference or update existing saved preference for the user
    public Preference saveOrUpdatePreferences(User user, Preference newPreference) {
        Preference existingPreferences = preferenceRepository.findByUser(user);

        if (newPreference.getInvestmentAmount() < 1) {
            throw new IllegalArgumentException("Investment amount must be a positive number.");
        }

        if (existingPreferences != null) {
            // Update existing preference
            existingPreferences.setInvestmentAmount(newPreference.getInvestmentAmount());
            existingPreferences.setTimeFrame(newPreference.getTimeFrame());
            existingPreferences.setFrequency(newPreference.getFrequency());
            existingPreferences.setRiskTolerance(newPreference.getRiskTolerance());
        } else {
            // Create new preferences entry
            existingPreferences = newPreference;
            existingPreferences.setUser(user);
        }

        return preferenceRepository.save(existingPreferences);
    }

    // Get the saved preferences for the user
    public Preference getPreferencesForUser(User user) {
        Preference preference = preferenceRepository.findByUser(user);
        if (preference == null) {
            throw new DataNotFoundException("No preferences found for user");
        }
        return preference;
    }

}
