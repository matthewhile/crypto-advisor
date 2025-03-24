package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.ExpenseEntity;
import com.cryptomaximizer.crypto_maximization_app.Model.UserEntity;

import com.cryptomaximizer.crypto_maximization_app.Repository.ExpenseRepository;
import com.cryptomaximizer.crypto_maximization_app.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseController(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseEntity>> loadExpenses(Authentication authentication) {
        UserEntity user = userService.getAuthenticatedUser(authentication);
        List<ExpenseEntity> expenses = expenseRepository.getExpensesByUserId(user.getId());
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseEntity> addExpense(@RequestBody ExpenseEntity expense, Authentication authentication) {
        UserEntity user = userService.getAuthenticatedUser(authentication);
        expense.setUser(user); // Associate expense with logged-in user
        ExpenseEntity savedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(savedExpense);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable Long id, @RequestBody ExpenseEntity updatedExpense) {
        Optional<ExpenseEntity> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ExpenseEntity existingExpense = optionalExpense.get();
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setAmount(updatedExpense.getAmount());

        ExpenseEntity savedExpense = expenseRepository.save(existingExpense);
        return ResponseEntity.ok(savedExpense);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        Optional<ExpenseEntity> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        expenseRepository.delete(optionalExpense.get());
        return ResponseEntity.ok("Expense deleted successfully");
    }
}

