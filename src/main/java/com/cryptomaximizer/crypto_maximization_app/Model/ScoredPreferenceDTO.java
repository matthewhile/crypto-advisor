package com.cryptomaximizer.crypto_maximization_app.Model;

public class ScoredPreferenceDTO {
    private double riskToleranceScore;
    private double frequencyScore;
    private double timeFrameScore;
    private double investmentAmountScore;
    private double totalScore;

    public ScoredPreferenceDTO() {
    }

    public ScoredPreferenceDTO(double riskToleranceScore, double frequencyScore,
                               double timeFrameScore, double investmentAmountScore, double totalScore) {
        this.riskToleranceScore = riskToleranceScore;
        this.frequencyScore = frequencyScore;
        this.timeFrameScore = timeFrameScore;
        this.investmentAmountScore = investmentAmountScore;
        this.totalScore = totalScore;
    }

    public double getRiskToleranceScore() {
        return riskToleranceScore;
    }

    public void setRiskToleranceScore(double riskToleranceScore) {
        this.riskToleranceScore = riskToleranceScore;
    }

    public double getFrequencyScore() {
        return frequencyScore;
    }

    public void setFrequencyScore(double frequencyScore) {
        this.frequencyScore = frequencyScore;
    }

    public double getTimeFrameScore() {
        return timeFrameScore;
    }

    public void setTimeFrameScore(double timeFrameScore) {
        this.timeFrameScore = timeFrameScore;
    }

    public double getInvestmentAmountScore() {
        return investmentAmountScore;
    }

    public void setInvestmentAmountScore(double investmentAmountScore) {
        this.investmentAmountScore = investmentAmountScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}

