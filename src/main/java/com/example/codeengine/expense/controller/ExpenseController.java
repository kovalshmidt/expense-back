package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.Mapper.Mapper;
import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.model.ExpenseViewModel;
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
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ExpenseController {
    private ExpenseRepository expenseRepository;
    private Mapper mapper;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository, Mapper mapper) {
        this.expenseRepository = expenseRepository;
        this.mapper = mapper;
    }

    @GetMapping("/expenses")
    public Collection<Expense> expenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity getExpense(@PathVariable("id") String id) {
        Optional<Expense> expense = expenseRepository.findById(UUID.fromString(id));
        return expense.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> expense(@Valid @RequestBody ExpenseViewModel expenseViewModel) throws URISyntaxException {
        Expense expense = mapper.convertToExpenseEntity(expenseViewModel);
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId())).body(result);
    }

    @PutMapping("/expense")
    public ResponseEntity<Expense> updateExpense(@Valid @RequestBody Expense expense) {
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        expenseRepository.deleteById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }
}
