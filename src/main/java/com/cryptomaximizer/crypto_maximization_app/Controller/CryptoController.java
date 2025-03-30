package com.cryptomaximizer.crypto_maximization_app.Controller;
import com.cryptomaximizer.crypto_maximization_app.Model.ChartDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoScoringService;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    private final CryptoScoringService cryptoScoringService;

    public CryptoController(CryptoService cryptoService, CryptoScoringService cryptoScoringService) {
        this.cryptoService = cryptoService;
        this.cryptoScoringService = cryptoScoringService;
    }

    @GetMapping("/market")
    public List<MarketDataDTO> getMarketData() {
        return cryptoService.getMarketData();
    }

    // For testing calculateTotalScore()
//    @GetMapping("/market")
//    public List<ScoredCryptoDTO> getMarketData() {
//        List<MarketDataDTO> marketDataList = cryptoService.getMarketData(); // Fetching market data
//        return cryptoScoringService.calculateTotalScore(marketDataList); // Calculate scores
//    }

    @GetMapping("/chart")
    public Map<String, ChartDataDTO> getMarketChart() {
        return cryptoService.getMarketChart();
    }
}
