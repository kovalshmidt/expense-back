package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ExpenseController {
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    public Collection<Expense> expenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity getExpense(@PathVariable("id") Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> expense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId())).body(result);
    }

    @PutMapping("/expense")
    public ResponseEntity<Expense> updateExpense(@Valid @RequestBody Expense expense) {
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
