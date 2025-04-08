package com.cryptomaximizer.crypto_maximization_app.Repository;

import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findByUser(User user);
}
