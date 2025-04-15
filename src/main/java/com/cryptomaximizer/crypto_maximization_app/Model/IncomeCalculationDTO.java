package com.cryptomaximizer.crypto_maximization_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeCalculationDTO {

    private String country;
    private String region;
    private double income;

    private double calculatedNetIncome;

    private double estimatedStateTax;

    private double totalTaxes;

    @JsonProperty("taxable_income")
    private double taxableIncome;

    @JsonProperty("federal_taxes_owed")
    private double federalTaxesOwed;

    @JsonProperty("fica_social_security")
    private double ficaSocialSecurity;

    @JsonProperty("fica_medicare")
    private double ficaMedicare;

    @JsonProperty("fica_total")
    private double ficaTotal;

    public IncomeCalculationDTO() {
    }

    // === Getters and setters ===

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getTaxableIncome() {
        return taxableIncome;
    }

    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double getFederalTaxesOwed() {
        return federalTaxesOwed;
    }

    public void setFederalTaxesOwed(double federalTaxesOwed) {
        this.federalTaxesOwed = federalTaxesOwed;
    }

    public double getFicaSocialSecurity() { return ficaSocialSecurity; }

    public void setFicaSocialSecurity(double ficaSocialSecurity) { this.ficaSocialSecurity = ficaSocialSecurity; }

    public double getFicaMedicare() { return ficaMedicare; }

    public void setFicaMedicare(double ficaMedicare) { this.ficaMedicare = ficaMedicare; }

    public double getFicaTotal() {
        return ficaTotal;
    }

    public void setFicaTotal(double ficaTotal) {
        this.ficaTotal = ficaTotal;
    }

    public double getEstimatedStateTax() { return estimatedStateTax; }
    public void setEstimatedStateTax(double estimatedStateTax) { this.estimatedStateTax = estimatedStateTax; }

    public double getTotalTaxes() { return totalTaxes; }

    public void setTotalTaxes(double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public double getCalculatedNetIncome() { return calculatedNetIncome; }

    public void setCalculatedNetIncome(double calculatedNetIncome) { this.calculatedNetIncome = calculatedNetIncome; }

}
