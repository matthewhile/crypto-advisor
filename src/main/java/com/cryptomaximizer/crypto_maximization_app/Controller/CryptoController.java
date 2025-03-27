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

    @GetMapping("/market/{symbol}")
    public List<MarketDataDTO> getMarketData(@PathVariable String symbol) {
        System.out.println("Received symbol: '" + symbol + "'");
        return cryptoService.getMarketData(symbol);
    }

    @GetMapping("/chart/{symbol}")
    public ChartDataDTO getMarketChart(@PathVariable String symbol) {
        return cryptoService.getMarketChart(symbol);
    }
}
