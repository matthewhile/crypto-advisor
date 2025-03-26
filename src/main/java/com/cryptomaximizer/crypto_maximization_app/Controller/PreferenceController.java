package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.PreferenceRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.PreferenceService;
import com.cryptomaximizer.crypto_maximization_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;
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
        Preference savedPreferences = preferenceService.saveOrUpdatePreferences(user, newPreference);
        return ResponseEntity.ok(savedPreferences);
    }

}
