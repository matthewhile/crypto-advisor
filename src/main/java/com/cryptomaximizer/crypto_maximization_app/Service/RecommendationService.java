package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredPreferenceDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {
    public List<ScoredCryptoDTO> getTopMatches(ScoredPreferenceDTO userPreference, List<ScoredCryptoDTO> cryptoList) {
        // Store each crypto and its score difference from the user's preference in a map
        Map<ScoredCryptoDTO, Double> scoreDifferences = new HashMap<>();

        // Compute the absolute difference between the user's total score and the crypto's score
        for (ScoredCryptoDTO crypto : cryptoList) {
            double userScore = userPreference.getTotalScore();
            double cryptoScore = crypto.getScore();
            double difference = Math.abs(userScore - cryptoScore);

            // Store each difference in the map using the crypto as the key
            scoreDifferences.put(crypto, difference);
        }

        // Convert the map entries to a sortable list
        List<Map.Entry<ScoredCryptoDTO, Double>> sortedEntries = new ArrayList<>(scoreDifferences.entrySet());
        // Sort the list in ascending order of score difference (closest matches at the beginning)
        sortedEntries.sort(Map.Entry.comparingByValue());

        List<ScoredCryptoDTO> topMatches = new ArrayList<>();
        // Loop through the first 3 entries in the sorted list
        for (int i = 0; i < Math.min(3, sortedEntries.size()); i++) {
            topMatches.add(sortedEntries.get(i).getKey()); // add the first 3 elements to topMatches
        }

        return topMatches;
    }

}
