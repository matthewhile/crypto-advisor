package com.cryptomaximizer.crypto_maximization_app.Model;

import java.util.List;

public class ChartDataDTO {
    private List<List<Object>> prices;
    private List<List<Object>> marketCaps;
    private List<List<Object>> totalVolumes;

    // Getters and setters
    public List<List<Object>> getPrices() { return prices; }
    public void setPrices(List<List<Object>> prices) { this.prices = prices; }

    public List<List<Object>> getMarketCaps() { return marketCaps; }
    public void setMarketCaps(List<List<Object>> marketCaps) { this.marketCaps = marketCaps; }

    public List<List<Object>> getTotalVolumes() { return totalVolumes; }
    public void setTotalVolumes(List<List<Object>> totalVolumes) { this.totalVolumes = totalVolumes; }
}
