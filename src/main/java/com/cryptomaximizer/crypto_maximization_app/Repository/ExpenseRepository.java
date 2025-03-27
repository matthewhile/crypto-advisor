package com.cryptomaximizer.crypto_maximization_app.Repository;

import java.util.List;
import com.cryptomaximizer.crypto_maximization_app.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> getExpensesByUserId(Long userId);
}
