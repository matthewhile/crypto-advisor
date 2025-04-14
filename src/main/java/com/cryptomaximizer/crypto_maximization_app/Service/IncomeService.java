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


        String filing_status = income.getFilingStatus();
        switch (filing_status) {
            case ("Single"):
                filing_status = "single";
                break;
            case ("Married Filing Jointly"):
                filing_status = "married";
                break;
            case ("Head of Household"):
                filing_status = "head_of_household";
                break;
            default:
                filing_status = "single";
        }

        System.out.println("DEBUG - Filing Status: " + filing_status);
        System.out.println("DEBUG - State: " + income.getState());
        System.out.println("DEBUG - Gross Income: " + income.getGrossIncome());

        String url = UriComponentsBuilder
                .fromUriString("https://api.api-ninjas.com/v1/incometaxcalculator")
                .queryParam("country", "US")
                .queryParam("income", income.getGrossIncome().intValue())
                .queryParam("region", income.getState())
                .queryParam("filing_status", filing_status)
                .toUriString();
        System.out.println("➡️ Final API URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<IncomeCalculationDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, requestEntity, IncomeCalculationDTO.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error calling income tax API: " + e.getMessage(), e);
        }
    }

    public Income saveOrUpdateIncome(User user, Income newIncome) {
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
