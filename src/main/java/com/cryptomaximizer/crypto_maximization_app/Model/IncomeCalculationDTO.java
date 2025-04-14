package com.cryptomaximizer.crypto_maximization_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeCalculationDTO {

    private String state;

    @JsonProperty("filing_status")
    private String filingStatus;

    private int income;

    @JsonProperty("federal_tax")
    private double federalTax;

    @JsonProperty("state_tax")
    private double stateTax;

    @JsonProperty("social_security_tax")
    private double socialSecurityTax;

    @JsonProperty("medicare_tax")
    private double medicareTax;

    @JsonProperty("total_taxes")
    private double totalTaxes;

    @JsonProperty("after_tax_income")
    private double afterTaxIncome;

    public IncomeCalculationDTO() {
    }

    // ✅ All getters and setters use camelCase method names

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFilingStatus() {
        return filingStatus;
    }

    public void setFilingStatus(String filingStatus) {
        this.filingStatus = filingStatus;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public double getFederalTax() {
        return federalTax;
    }

    public void setFederalTax(double federalTax) {
        this.federalTax = federalTax;
    }

    public double getStateTax() {
        return stateTax;
    }

    public void setStateTax(double stateTax) {
        this.stateTax = stateTax;
    }

    public double getSocialSecurityTax() {
        return socialSecurityTax;
    }

    public void setSocialSecurityTax(double socialSecurityTax) {
        this.socialSecurityTax = socialSecurityTax;
    }

    public double getMedicareTax() {
        return medicareTax;
    }

    public void setMedicareTax(double medicareTax) {
        this.medicareTax = medicareTax;
    }

    public double getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public double getAfterTaxIncome() {
        return afterTaxIncome;
    }

    public void setAfterTaxIncome(double afterTaxIncome) {
        this.afterTaxIncome = afterTaxIncome;
    }
}
