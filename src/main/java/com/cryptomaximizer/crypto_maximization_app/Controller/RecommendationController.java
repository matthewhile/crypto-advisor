package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.*;
import com.cryptomaximizer.crypto_maximization_app.Repository.RecommendationRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationRepository recommendationRepository;
    private final UserService userService;
    private final PreferenceDataService preferenceDataService;
    private final PreferenceScoringService preferenceScoringService;
    private final CryptoDataService cryptoDataService;
    private final CryptoScoringService cryptoScoringService;


    @Autowired
    public RecommendationController(RecommendationService recommendationService, RecommendationRepository recommendationRepository, UserService userService,
                                    PreferenceDataService preferenceDataService, PreferenceScoringService preferenceScoringService, CryptoDataService cryptoDataService,
                                    CryptoScoringService cryptoScoringService)
    {
        this.recommendationService = recommendationService;
        this.recommendationRepository = recommendationRepository;
        this.userService = userService;
        this.preferenceDataService = preferenceDataService;
        this.preferenceScoringService = preferenceScoringService;
        this.cryptoDataService = cryptoDataService;
        this.cryptoScoringService = cryptoScoringService;
    }

    @GetMapping
    public ResponseEntity<RecommendationRespDTO> getTopCryptoMatches(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        // Get user preference score & explanation
        Preference userPreferences = preferenceDataService.getPreferencesForUser(user);
        ScoredPreferenceDTO scoredPreference = preferenceScoringService.calculatePreferenceScore(userPreferences);
        String explanation = preferenceScoringService.generateExplanation(scoredPreference, userPreferences);

        // Get Crypto scores
        List<MarketDataDTO> marketDataList = cryptoDataService.getMarketData();
        List<ScoredCryptoDTO> scoredCryptos = cryptoScoringService.calculateCryptoScore(marketDataList);

        // Get top matches
        List<ScoredCryptoDTO> topMatches = recommendationService.getTopMatches(scoredPreference, scoredCryptos);

        RecommendationRespDTO response = new RecommendationRespDTO(userPreferences, topMatches, explanation);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/load")
    public ResponseEntity<List<Recommendation>> loadRecommendations(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        List<Recommendation> recommendations = recommendationRepository.getSavedRecommendationsByUserId(user.getId());
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/save")
    public ResponseEntity<Recommendation> saveRecommendation(@RequestBody Recommendation recommendation, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        recommendation.setUser(user); // Associate recommendation with logged-in user
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return ResponseEntity.ok(savedRecommendation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRecommendation(@PathVariable Long id) {
        Optional<Recommendation> recommendationOptional = recommendationRepository.findById(id);

        if (recommendationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recommendation not found");
        }

        recommendationRepository.delete(recommendationOptional.get());
        return ResponseEntity.ok("Recommendation deleted successfully");
     }

}
