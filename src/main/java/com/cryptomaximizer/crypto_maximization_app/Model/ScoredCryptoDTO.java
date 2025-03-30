package com.cryptomaximizer.crypto_maximization_app.Model;

public class ScoredCryptoDTO {
    private MarketDataDTO crypto;
    private double score;

    public ScoredCryptoDTO(MarketDataDTO crypto, double score) {
        this.crypto = crypto;
        this.score = score;
    }

    public MarketDataDTO getCrypto() {
        return crypto;
    }

    public double getScore() {
        return score;
    }
}

