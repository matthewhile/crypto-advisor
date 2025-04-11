package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Exception.DataNotFoundException;
import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

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
