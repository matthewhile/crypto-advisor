package com.cryptomaximizer.crypto_maximization_app.Model;

import java.math.BigDecimal;

public class ExpenseDTO {
    private Long id;
    private String category;
    private BigDecimal amount;

    public ExpenseDTO(Long id, String category, BigDecimal amount) {
        this.id = id;
        this.category = category;
        this.amount = amount;
    }

    // Getters & Setters

}

