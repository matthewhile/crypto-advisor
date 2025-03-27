package com.cryptomaximizer.crypto_maximization_app.Repository;

import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Preference findByUser(User user);
}
