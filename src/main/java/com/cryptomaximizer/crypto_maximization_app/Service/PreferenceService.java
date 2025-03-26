package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public Preference saveOrUpdatePreferences(User user, Preference newPreference) {
        Preference existingPreferences = preferenceRepository.findByUser(user);

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
}
