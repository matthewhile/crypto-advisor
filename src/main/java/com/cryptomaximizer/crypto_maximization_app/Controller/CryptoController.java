package com.cryptomaximizer.crypto_maximization_app.Controller;
import com.cryptomaximizer.crypto_maximization_app.Model.ChartDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.ScoredCryptoDTO;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoScoringService;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoDataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoDataService cryptoDataService;

    private final CryptoScoringService cryptoScoringService;

    public CryptoController(CryptoDataService cryptoDataService, CryptoScoringService cryptoScoringService) {
        this.cryptoDataService = cryptoDataService;
        this.cryptoScoringService = cryptoScoringService;
    }

    // Fetch market data
    @GetMapping("/market")
    public List<MarketDataDTO> getCryptoData() {
        return cryptoDataService.getMarketData();
    }

    // Fetch chart data
    @GetMapping("/chart")
    public Map<String, ChartDataDTO> getMarketChart() {
        return cryptoDataService.getMarketChart();
    }
}
