package com.cryptomaximizer.crypto_maximization_app.Service;

import com.cryptomaximizer.crypto_maximization_app.Model.Income;
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
            existingIncome.setGrossIncome(newIncome.getGrossIncome());
        } else {
            existingIncome = newIncome;
            existingIncome.setUser(user);
        }
        return incomeRepository.save(existingIncome);
    }
}
