package com.example.codeengine.expense.controller;

import com.example.codeengine.expense.mapper.Mapper;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/expense")
public class ExpenseController {
    private ExpenseRepository expenseRepository;
    private Mapper mapper;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository, Mapper mapper) {
        this.expenseRepository = expenseRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public Collection<ExpenseViewModel> expenses() {
        Collection<Expense> expenses = this.expenseRepository.findAll();
        return expenses.stream().map(expense -> this.mapper.convertToExpenseViewModel(expense)).collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getExpense(@PathVariable("id") String id) {
        Optional<Expense> expense = expenseRepository.findById(UUID.fromString(id));
        return expense.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<ExpenseViewModel> expense(@Valid @RequestBody ExpenseViewModel expenseViewModel) throws URISyntaxException {
        //Convert ExpenseViewModel to Expense
        Expense expense = mapper.convertToExpenseEntity(expenseViewModel);
        //Save Expense in Database
        Expense expenseWithId = expenseRepository.save(expense);
        //Convert Expense to ExpenseViewModel
        ExpenseViewModel expenseViewModel1 = mapper.convertToExpenseViewModel(expenseWithId);
        return ResponseEntity.created(new URI("/api/expenses/" + expenseViewModel1.getId())).body(expenseViewModel1);
    }

    @PutMapping("/update")
    public ResponseEntity<ExpenseViewModel> updateExpense(@Valid @RequestBody ExpenseViewModel expenseView) {
        //Convert ExpenseViewModel to Expense
        Expense expense = mapper.convertToExpenseEntity(expenseView);
        //Save Expense in Database
        Expense expenseFromDB = expenseRepository.save(expense);
        //Convert Expense to ExpenseViewModel
        ExpenseViewModel expenseViewModel = this.mapper.convertToExpenseViewModel(expenseFromDB);
        return ResponseEntity.ok().body(expenseViewModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        expenseRepository.deleteById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }
}
