package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.*;
import com.cryptomaximizer.crypto_maximization_app.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;
    private final PreferenceDataService preferenceDataService;
    private final PreferenceScoringService preferenceScoringService;
    private final CryptoDataService cryptoDataService;
    private final CryptoScoringService cryptoScoringService;


    @Autowired
    public RecommendationController(RecommendationService recommendationService, UserService userService, PreferenceDataService preferenceDataService, PreferenceScoringService preferenceScoringService, CryptoDataService cryptoDataService, CryptoScoringService cryptoScoringService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
        this.preferenceDataService = preferenceDataService;
        this.preferenceScoringService = preferenceScoringService;
        this.cryptoDataService = cryptoDataService;
        this.cryptoScoringService = cryptoScoringService;
    }

    @PostMapping("/recommendations")
    public ResponseEntity<List<ScoredCryptoDTO>> getTopCryptoMatches(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        // Get user preference score
        Preference userPreferences = preferenceDataService.getPreferencesForUser(user);
        ScoredPreferenceDTO scoredPreference = preferenceScoringService.calculatePreferenceScore(userPreferences);
        // Get Crypto scores
        List<MarketDataDTO> marketDataList = cryptoDataService.getMarketData();
        List<ScoredCryptoDTO> scoredCryptos = cryptoScoringService.calculateCryptoScore(marketDataList);

        // Get top matches
        List<ScoredCryptoDTO> topMatches = recommendationService.getTopMatches(scoredPreference, scoredCryptos);

        return ResponseEntity.ok(topMatches);
    }

}
