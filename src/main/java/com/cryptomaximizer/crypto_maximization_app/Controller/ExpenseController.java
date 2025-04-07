package com.cryptomaximizer.crypto_maximization_app.Controller;

import com.cryptomaximizer.crypto_maximization_app.Model.Expense;
import com.cryptomaximizer.crypto_maximization_app.Model.User;

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

    // Load all expenses
    @GetMapping
    public ResponseEntity<List<Expense>> loadExpenses(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        List<Expense> expenses = expenseRepository.getExpensesByUserId(user.getId());
        return ResponseEntity.ok(expenses);
    }

    // Add a new expense
    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        expense.setUser(user); // Associate expense with logged-in user
        Expense savedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(savedExpense);
    }

    // Update selected expense
    @PutMapping("/update/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Expense existingExpense = optionalExpense.get();
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setAmount(updatedExpense.getAmount());

        Expense savedExpense = expenseRepository.save(existingExpense);
        return ResponseEntity.ok(savedExpense);
    }

    // Delete selected expense
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        expenseRepository.delete(optionalExpense.get());
        return ResponseEntity.ok("Expense deleted successfully");
    }
}

