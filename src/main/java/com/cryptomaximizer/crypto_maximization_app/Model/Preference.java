package com.cryptomaximizer.crypto_maximization_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    @Column(name = "investment_amount", nullable = false)
    private Double investmentAmount;

    @Column(name = "time_frame", nullable = false)
    private String timeFrame;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "risk_tolerance", nullable = false)
    private String riskTolerance;

    public Preference() { }

    public Preference(User user, Double investmentAmount, String timeFrame, String frequency, String riskTolerance) {
        this.user = user;
        this.investmentAmount = investmentAmount;
        this.timeFrame = timeFrame;
        this.frequency = frequency;
        this.riskTolerance = riskTolerance;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Double getInvestmentAmount() { return investmentAmount; }

    public void setInvestmentAmount(Double investmentAmount) { this.investmentAmount = investmentAmount; }

    public String getTimeFrame() { return timeFrame; }

    public void setTimeFrame(String timeFrame) { this.timeFrame = timeFrame; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getRiskTolerance() { return riskTolerance; }

    public void setRiskTolerance(String riskTolerance) { this.riskTolerance = riskTolerance; }
}
