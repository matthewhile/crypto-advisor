package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.PreferenceRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.PreferenceDataService;
import com.cryptomaximizer.crypto_maximization_app.Service.PreferenceScoringService;
import com.cryptomaximizer.crypto_maximization_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceDataService preferenceDataService;
    @Autowired
    private PreferenceScoringService preferenceScoringService;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private final UserService userService;

    public PreferenceController(PreferenceRepository preferenceRepository, UserService userService) {
        this.preferenceRepository = preferenceRepository;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Preference> setPreferences(@RequestBody Preference newPreference, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Preference savedPreferences = preferenceDataService.saveOrUpdatePreferences(user, newPreference);
        //preferenceScoringService.calculatePreferenceScore(newPreference); // For testing preference scores
        return ResponseEntity.ok(savedPreferences);
    }

    @GetMapping
    public ResponseEntity<Preference> getPreferences(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Preference preferences = preferenceDataService.getPreferencesForUser(user);
        return ResponseEntity.ok(preferences);
    }


}
