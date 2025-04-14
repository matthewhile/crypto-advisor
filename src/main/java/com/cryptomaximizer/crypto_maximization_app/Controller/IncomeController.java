package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.IncomeCalculationDTO;
import com.cryptomaximizer.crypto_maximization_app.Model.Preference;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.IncomeRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.IncomeService;
import com.cryptomaximizer.crypto_maximization_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    private IncomeRepository incomeRepository;
    private UserService userService;

    public IncomeController(IncomeRepository incomeRepository, UserService userService){
        this.incomeRepository = incomeRepository;
        this.userService = userService;
    }

    // Store user income info in database, update existing income info it already exists
    @PostMapping
    public ResponseEntity<Income> setIncome(@RequestBody Income newIncome, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Income savedIncome = incomeService.saveOrUpdateIncome(user, newIncome);
        return ResponseEntity.ok(savedIncome);
    }

    @GetMapping
    public ResponseEntity<Income> getIncome(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Income income = incomeService.getTaxInfoForUser(user);
        return ResponseEntity.ok(income);
    }

    @GetMapping("/taxes")
    public ResponseEntity<IncomeCalculationDTO> getIncomeTaxes(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Income income = incomeService.getTaxInfoForUser(user);
        IncomeCalculationDTO taxData = incomeService.fetchIncomeTaxCalculation(income);
        return ResponseEntity.ok(taxData);
    }


}
