package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.Income;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import com.cryptomaximizer.crypto_maximization_app.Repository.IncomeRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.IncomeService;
import com.cryptomaximizer.crypto_maximization_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
