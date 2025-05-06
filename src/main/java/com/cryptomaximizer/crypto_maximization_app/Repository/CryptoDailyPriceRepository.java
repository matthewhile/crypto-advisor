package com.cryptomaximizer.crypto_maximization_app.Repository;

import com.cryptomaximizer.crypto_maximization_app.Model.CryptoDailyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CryptoDailyPriceRepository extends JpaRepository<CryptoDailyPrice, Long> {

    // Check if a price entry already exists for a given crypto symbol and date
    boolean existsBySymbolAndDate(String symbol, LocalDate date);

    // Fetch all daily prices for a crypto between two dates (for historical volatility calculation)
    List<CryptoDailyPrice> findBySymbolAndDateBetween(String symbol, LocalDate startDate, LocalDate endDate);

    // Fetch all daily prices for a date range across all symbols
    List<CryptoDailyPrice> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
