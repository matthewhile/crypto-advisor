package com.cryptomaximizer.crypto_maximization_app.Model;

import java.util.List;

public class RecommendationRespDTO {
    private Preference userPreferences;
    private List<ScoredCryptoDTO> topMatches;

    public RecommendationRespDTO() {}

    public RecommendationRespDTO(Preference userPreferences, List<ScoredCryptoDTO> topMatches) {
        this.userPreferences = userPreferences;
        this.topMatches = topMatches;
    }

    public Preference getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(Preference userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<ScoredCryptoDTO> getTopMatches() {
        return topMatches;
    }

    public void setTopMatches(List<ScoredCryptoDTO> topMatches) {
        this.topMatches = topMatches;
    }
}
