package com.cryptomaximizer.crypto_maximization_app.Repository;

import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Get the saved income for the user
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findByUser(User user);
}
