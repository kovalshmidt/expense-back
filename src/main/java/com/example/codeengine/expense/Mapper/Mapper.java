package com.example.codeengine.expense.Mapper;

import com.example.codeengine.expense.model.Category;
import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.model.ExpenseViewModel;
import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.CategoryRepository;
import com.example.codeengine.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
public class Mapper {

    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public Mapper(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method receives an ExpenseViewModel as parameter and converts it to an Expense object
     */
    public Expense convertToExpenseEntity(ExpenseViewModel expenseViewModel) {

        //Create a new empty Expense object
        Expense expense = new Expense();

        //Convert and set id to Expense object
        String id = expenseViewModel.getId();
        if (id != null) {
            UUID uuid = UUID.fromString(id);
            expense.setId(uuid);
        }

        //Convert and set expenseDate to Expense object
        String expenseDate = expenseViewModel.getExpenseDate();
        if (expenseDate != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //create a formatter for the format that we expect
            LocalDate ld = LocalDate.parse(expenseDate, dateTimeFormatter);
            LocalDateTime expenseLocal = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
            expense.setExpenseDate(expenseLocal);
        }

        //Convert and set category to Expense object
        String categoryId = expenseViewModel.getCategoryId(); //Get category id from expenseViewModel
        if (categoryId != null) {
            UUID categoryUUUID = UUID.fromString(categoryId); //Convert categoryId type string to categoryUUID type UUID
            Optional<Category> optional = categoryRepository.findById(categoryUUUID); //Find category by id in database
            if (optional.isPresent()) { //If category is present
                Category category = optional.get(); //Get category from optional
                expense.setCategory(category); //Set category in expense
            }
        }

        //Set location to Expense object
        String location = expenseViewModel.getLocation();
        expense.setLocation(location);

        //Set description to Expense object
        expense.setDescription(expenseViewModel.getDescription());

        //Set user to Expense object
        String userId = expenseViewModel.getUserId();
        if (userId != null) {
            UUID userUUID = UUID.fromString(userId); //Convert userId type string to userUUID type UUID
            Optional<User> optional = userRepository.findById(userUUID); //Find user by id in database
            if (optional.isPresent()) { //If category is present
                User user = optional.get(); //Get category from optional
                expense.setUser(user); //Set category in expense
            }
        }

        //Return Expense object with data from ExpenseViewModel
        return expense;
    }
}
