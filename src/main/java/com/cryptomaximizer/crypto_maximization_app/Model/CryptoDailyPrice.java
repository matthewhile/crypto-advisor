package com.cryptomaximizer.crypto_maximization_app.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "crypto_daily_prices")
public class CryptoDailyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "high_24h", nullable = false)
    private double high24h;

    @Column(name = "low_24h", nullable = false)
    private double low24h;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(double high24h) {
        this.high24h = high24h;
    }

    public double getLow24h() {
        return low24h;
    }

    public void setLow24h(double low24h) {
        this.low24h = low24h;
    }
}

