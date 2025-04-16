package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Exception.DataNotFoundException;
import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.IncomeCalculationDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
public class IncomeService {

    private final RestTemplate restTemplate;

    @Value("${income.calculator.key}") // Load API key from application.properties
    private String apiKey;

    public IncomeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private IncomeRepository incomeRepository;

    public IncomeCalculationDTO fetchIncomeTaxCalculation(Income income) {

        double grossIncome = income.getGrossIncome().doubleValue();
        String state = income.getState();
        String filing_status = income.getFilingStatus();
        double standardDeduction = 0;

        switch (filing_status) {
            case ("Single"):
                filing_status = "single";
                standardDeduction = 14600;
                break;
            case ("Married Filing Jointly"):
                filing_status = "married";
                standardDeduction = 29200;
                break;
            case ("Head of Household"):
                filing_status = "head_of_household";
                standardDeduction = 21900;
                break;
            default:
                filing_status = "single";
                standardDeduction = 14600;
        }

        double taxableIncome = Math.max(0, grossIncome - standardDeduction);

        String url = UriComponentsBuilder
                .fromUriString("https://api.api-ninjas.com/v1/incometaxcalculator")
                .queryParam("country", "US")
                .queryParam("income", grossIncome)
                .queryParam("region", state)
                .queryParam("filing_status", filing_status)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<IncomeCalculationDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, IncomeCalculationDTO.class
        );
        IncomeCalculationDTO dto = response.getBody();

        if (dto != null) {

            // Get estimated state taxes
            double stateTax = getEstimatedStateTax(state, grossIncome);
            dto.setEstimatedStateTax(stateTax);

            // Calculate total taxes owed
            double federalTax = dto.getFederalTaxesOwed();
            double ficaTax = dto.getFicaTotal();
            double totalTax = stateTax + federalTax + ficaTax;
            dto.setTotalTaxes(totalTax);

            // Calculate net income
            double netIncome = grossIncome - totalTax;
            dto.setCalculatedNetIncome(netIncome);

            // Add or update net income in the Income db table
            income.setNetIncome(BigDecimal.valueOf(netIncome));
            incomeRepository.save(income); // update the row with new net income
        }

        return dto;
    }

    public double getEstimatedStateTax(String state, double income) {
        // We calculate state income tax only for states with a fixed tax rate for proof of concept.
        // States that use a graduated tax rate are excluded because I don't want to spend $20 on the API for this project.
        switch (state.toUpperCase()) {
            case "AZ":
                return income * 0.025; // Arizona
            case "CO":
                return income * 0.0455; // Colorado
            case "GA":
                return income * 0.0575; // Georgia
            case "ID":
                return income * 0.058; // Idaho
            case "IL":
                return income * 0.0495; // Illinois
            case "IN":
                return income * 0.0323; // Indiana
            case "IA":
                return income * 0.06; // Iowa
            case "KY":
                return income * 0.05; // Kentucky
            case "MI":
                return income * 0.0425; // Michigan
            case "MS":
                return income * 0.05; // Mississippi
            case "NC":
                return income * 0.0475; // North Carolina
            case "PA":
                return income * 0.0307; // Pennsylvania
            case "UT":
                return income * 0.0485; // Utah
            case "MA":
                return income * 0.05; // Massachusetts

            default:
                return 0.0; // For non-income-tax states like FL, TX, NV, etc.
        }
    }

    public Income saveOrUpdateTaxInfo(User user, Income newIncome) {
        Income existingIncome = incomeRepository.findByUser(user);

        if (existingIncome != null) {
            existingIncome.setState(newIncome.getState());
            existingIncome.setFilingStatus(newIncome.getFilingStatus());
            existingIncome.setGrossIncome(newIncome.getGrossIncome());
        } else {
            existingIncome = newIncome;
            existingIncome.setUser(user);
        }
        return incomeRepository.save(existingIncome);
    }

    public Income getTaxInfoForUser(User user) {
        Income income = incomeRepository.findByUser(user);
        if (income == null) {
            throw new DataNotFoundException("No income tax info found for user");
        }
        return income;
    }
}
