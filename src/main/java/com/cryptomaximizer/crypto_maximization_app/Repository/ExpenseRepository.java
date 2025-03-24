package com.cryptomaximizer.crypto_maximization_app.Repository;

import java.util.List;
import com.cryptomaximizer.crypto_maximization_app.Model.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> getExpensesByUserId(Long userId); // Fetch expenses by user ID
}
