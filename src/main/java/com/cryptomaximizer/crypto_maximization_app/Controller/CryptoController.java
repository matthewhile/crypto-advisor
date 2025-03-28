package com.cryptomaximizer.crypto_maximization_app.Controller;
import com.cryptomaximizer.crypto_maximization_app.Model.ChartDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.MarketDataDTO;
import com.cryptomaximizer.crypto_maximization_app.Service.CryptoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/market")
    public List<MarketDataDTO> getMarketData() {
        return cryptoService.getMarketData();
    }

    @GetMapping("/chart")
    public Map<String, ChartDataDTO> getMarketChart() {
        return cryptoService.getMarketChart();
    }
}
