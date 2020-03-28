package com.example.codeengine.expense.mapper;

import com.example.codeengine.expense.model.Category;
import com.example.codeengine.expense.model.Expense;
import com.example.codeengine.expense.model.ExpenseViewModel;
import com.example.codeengine.expense.model.User;
import com.example.codeengine.expense.repository.CategoryRepository;
import com.example.codeengine.expense.repository.UserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@SpringBootTest
class MapperTest {

    @Autowired
    private Mapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    private static SoftAssertions softAssertions;

    @BeforeAll
    static void setUp() {
        softAssertions = new SoftAssertions();
    }

    @Test
    void testConvertToExpenseEntity() {
        //Create ExpenseViewModel to populate it with data
        ExpenseViewModel expenseViewModel = new ExpenseViewModel();

        //Set Id to ExpenseViewModel
        String id = UUID.randomUUID().toString(); //Create a random UUID
        expenseViewModel.setId(id);

        //Set Description
        expenseViewModel.setDescription("This is a description");

        //Set Location
        expenseViewModel.setLocation("Kyiv");

        //Set Category
        Category category = new Category(); //Create a Category
        category.setName("Univer"); //Assign name to Category
        category = categoryRepository.save(category); //Save the category to Database and get in return the same category but with UUID assigned
        String categoryId = category.getId().toString(); //Get Category id as String
        expenseViewModel.setCategoryId(categoryId);

        //Set expenseDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //We create a formatter
        String currentDate = LocalDateTime.now().format(formatter); //We use the formatter to transform the date of now into the format
        expenseViewModel.setExpenseDate(currentDate);

        //Set User
        User user = new User(); //Create a User
        user.setName("Miha"); //Assign a name to the User
        user.setEmail("pavlo@pavlo.com"); //Assign email to the User
        user = userRepository.save(user); //Save the user to Database and get in return the same user but with UUID assigned
        String userId = user.getId().toString(); //Get User id as String
        expenseViewModel.setUserId(userId);

        //Convert populated ExpenseViewModel to Expense
        Expense expense = mapper.convertToExpenseEntity(expenseViewModel);

        //Check Description
        String description = expense.getDescription();
        softAssertions.assertThat(description).isEqualTo(expenseViewModel.getDescription());
        softAssertions.assertThat(description).isNotEqualTo(null);
        //Check Id
        String expenseId = expense.getId().toString();
        softAssertions.assertThat(expenseId).isEqualTo(expenseViewModel.getId());
        softAssertions.assertThat(expenseId).isNotEqualTo(null);
        //Check Location
        String location = expense.getLocation();
        softAssertions.assertThat(location).isEqualTo(expenseViewModel.getLocation());
        softAssertions.assertThat(location).isNotEqualTo(null);
        //Check Category Id
        String category_Id = expense.getCategory().getId().toString();
        softAssertions.assertThat(category_Id).isEqualTo(expenseViewModel.getCategoryId());
        softAssertions.assertThat(category_Id).isNotEqualTo(null);
        //Check User Id
        String user_Id = expense.getUser().getId().toString();
        softAssertions.assertThat(user_Id).isEqualTo(expenseViewModel.getUserId());
        softAssertions.assertThat(user_Id).isNotEqualTo(null);
    }

    @Test
    void convertToExpenseViewModel() {
        Expense expense = new Expense();
        //set id to Expense
        UUID id = UUID.randomUUID();
        expense.setId(id);

        //set description to expense
        expense.setDescription("desk");

        //set location to expense
        expense.setLocation("location");

        //set Category to Expense
        Category category = new Category();//create category
        category.setName("category");//set name to category
        category = categoryRepository.save(category);
        expense.setCategory(category);

        //set ExpenseDate to Expense
        expense.setExpenseDate(LocalDateTime.now());

        //Set user
        User user = new User();
        user.setName("kolya");//set name to Expense
        user.setEmail("gooogle@eamil.com");//set email to Expense
        user = userRepository.save(user);
        expense.setUser(user);

        //Convert populated Expense to ExpenseViewModel
        ExpenseViewModel expenseViewModel = mapper.convertToExpenseViewModel(expense);

        //Check Description(done)
        String description = expenseViewModel.getDescription();//Extract description from ExpenseViewModel
        softAssertions.assertThat(description).isEqualTo(expense.getDescription());//Check that description from ExpenseViewModel equals to the one in Expense
        softAssertions.assertThat(description).isNotEqualTo(null);//Check that description is not null
        //Check ID
        String expenseId = expenseViewModel.getId();///Extract expenseId from ExpenseViewModel
        softAssertions.assertThat(expenseId).isEqualTo(expense.getId().toString());//Check that description from ExpenseViewModel equals to the one in Expense, since types are different convert the id in Expense to String
        softAssertions.assertThat(expenseId).isNotEqualTo(null);//Check that description is not null
        //Check Location(done)
        String location = expenseViewModel.getLocation();//Extract location from ExpenseViewModel
        softAssertions.assertThat(location).isEqualTo(expense.getLocation());//Check that location from ExpenseViewModel equals to the one in Expense
        softAssertions.assertThat(location).isNotEqualTo(null);//Check that location is not null
        //Check Category
        String categoryId = expenseViewModel.getCategoryId();//Extract categoryId from ExpenseViewModel
        softAssertions.assertThat(categoryId).isEqualTo(expense.getCategory().getId().toString());//Check that categoryId from ExpenseViewModel is equal to the Category id that is in Expense, since types are different convert the id in Category to String
        softAssertions.assertThat(categoryId).isNotEqualTo(null);//Check that categoryId is not null
        //Check User
        String userId = expenseViewModel.getUserId();//Extract userId from ExpenseViewModel
        softAssertions.assertThat(userId).isEqualTo(expense.getUser().getId().toString());//Check that userId from ExpenseViewModel is equal to the User id that is in Expense, since types are different convert the id in User to String
        softAssertions.assertThat(userId).isNotEqualTo(null);//Check that userId is not null
    }

    @AfterAll
    static void afterAll() {
        softAssertions.assertAll();
    }
}
