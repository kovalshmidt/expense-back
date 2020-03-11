package com.example.codeengine.expense.Mapper;

import com.example.codeengine.expense.model.Category;
import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.model.ExpenseViewModel;
import com.example.codeengine.expense.repository.CategoryRepository;
import com.example.codeengine.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
@Component
public class Mapper {

    private CategoryRepository categoryRepository;

    @Autowired
    public Mapper (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Expense convertToExpenseEntity(ExpenseViewModel expenseViewModel) {

        Expense expense = new Expense();

        //Convert and set id
        String id = expenseViewModel.getId();
        if(id != null){
            UUID uuid = UUID.fromString(id);
            expense.setId(uuid);
        }

        //Convert and set expenseDate
        String expenseDate = expenseViewModel.getExpenseDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(expenseDate, dateTimeFormatter);
        LocalDateTime expenseLocal = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());;
        expense.setExpenseDate(expenseLocal);

        //Convert and set category
        String categoryId = expenseViewModel.getCategoryId(); //Get category id from expenseViewModel
        UUID categoryUuid = UUID.fromString(categoryId); //Convert categoryId type string to categoryId type UUID
        Optional<Category> optional = categoryRepository.findById(categoryUuid); //Find category by id in database
        if(optional.isPresent()){ //If category is present
            Category category = optional.get(); //Get category from optional
            expense.setCategory(category); //Set category in expense
        }

        //Set location
        String location = expenseViewModel.getLocation();
        expense.setLocation(location);

        //Set description
        expense.setDescription(expenseViewModel.getDescription());

        //Set user
        expense.setUser(null);

        return expense;


    }
}
