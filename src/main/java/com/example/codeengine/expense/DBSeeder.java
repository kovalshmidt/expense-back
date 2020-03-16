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
 * property noteit.db.recreate is set to true in the
 * application.properties file
 */

@Component
@ConditionalOnProperty(name = "expense.db.populate", havingValue = "true")
public class DBSeeder implements CommandLineRunner {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    public DBSeeder(UserRepository userRepository, CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
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

        System.out.println("Initialized database");
    }
}

