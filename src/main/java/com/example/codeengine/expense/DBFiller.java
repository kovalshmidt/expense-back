package com.example.codeengine.expense;

import com.example.codeengine.expense.model.Category;
import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.CategoryRepository;
import com.example.codeengine.expense.repository.ExpenseRepository;
import com.example.codeengine.expense.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This component will only execute (and get instantiated) if the
 * property expense.db.populate is set to true in the
 * application.properties file
 */

@Component
@ConditionalOnProperty(name = "expense.db.populate", havingValue = "true")
public class DBFiller implements CommandLineRunner {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    public DBFiller(UserRepository userRepository, CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }


    @Override
    public void run(String... args) {

        // Remove all existing users
        this.userRepository.deleteAll();
        // Save a admin user
        User user = new User();
        user.setEmail("admin@admin.com");
        user.setName("admin");
        this.userRepository.save(user);

        // Remove all existing categories
        this.categoryRepository.deleteAll();
        // Save the category
        Category category = new Category();
        category.setName("Travel");
        this.categoryRepository.save(category);

        // Save the category
        Category category1 = new Category();
        category1.setName("Virus");
        this.categoryRepository.save(category1);

        // Save the category
        Category category2 = new Category();
        category2.setName("Library");
        this.categoryRepository.save(category2);

        // Remove all existing expenses
        this.expenseRepository.deleteAll();
        // Save a the Expense
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setDescription("New York Business trip");
        expense.setUser(user);
        expense.setExpenseDate(LocalDateTime.now());
        expense.setLocation("New York");
        this.expenseRepository.save(expense);

        // Save a the Expense
        Expense expense1 = new Expense();
        expense1.setCategory(category1);
        expense1.setDescription("Corona virus");
        expense1.setUser(user);
        expense1.setExpenseDate(LocalDateTime.now());
        expense1.setLocation("China");
        this.expenseRepository.save(expense1);

        // Save a the Expense
        Expense expense2 = new Expense();
        expense2.setCategory(category2);
        expense2.setDescription("Books in university");
        expense2.setUser(user);
        expense2.setExpenseDate(LocalDateTime.now());
        expense2.setLocation("London");
        this.expenseRepository.save(expense2);

        System.out.println("Initialized database");
    }
}

