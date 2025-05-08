package com.cryptomaximizer.crypto_maximization_app.Repository;

import com.cryptomaximizer.crypto_maximization_app.Model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Return the user's saved recommendations
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> getSavedRecommendationsByUserId(Long userId);
}
