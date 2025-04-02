package com.cryptomaximizer.crypto_maximization_app.Model;

import java.util.List;

public class RecommendationRespDTO {
    private Preference userPreferences;
    private List<ScoredCryptoDTO> topMatches;
    private String explanation;

    public RecommendationRespDTO() {}

    public RecommendationRespDTO(Preference userPreferences, List<ScoredCryptoDTO> topMatches, String explanation) {
        this.userPreferences = userPreferences;
        this.topMatches = topMatches;
        this.explanation = explanation;
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

    public String getExplanation() { return explanation; }

    public void setExplanation(String explanation) { this.explanation = explanation; }
}
