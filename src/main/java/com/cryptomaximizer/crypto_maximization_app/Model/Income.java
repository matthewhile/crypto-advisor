package com.cryptomaximizer.crypto_maximization_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "gross_income", nullable = false)
    private BigDecimal grossIncome;

    public Income() { }

    public Income(User user, String state, BigDecimal grossIncome) {
        this.user = user;
        this.state = state;
        this.grossIncome = grossIncome;
    }


    public Long getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public BigDecimal getGrossIncome() { return grossIncome; }

    public void setGrossIncome(BigDecimal grossIncome) { this.grossIncome = grossIncome; }
}
