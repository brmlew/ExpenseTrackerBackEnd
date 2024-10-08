package com.expenses.ExpenseTracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense createExpense(String date, BigDecimal amount, String note, String category, String subcategory) {
    	// find category and subcategory based on the provided names
        ExpenseCategory expenseCategory= categoryRepository.findByCategoryName(category);
        ExpenseSubcategory expenseSubcategory = subcategoryRepository.findBySubcategoryNameAndCategory(subcategory, expenseCategory.getId());
        
        // add format to handle and parse the date
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date newDate = format.parse(date);
            System.out.println(amount);
            
            // create a new expense object and add it to the mongoDB database
            Expense expense = new Expense(newDate, amount, note, expenseCategory, expenseSubcategory);

            mongoTemplate.insert(expense);

            return expense;
        }
        catch (ParseException e) {
            System.out.printf("e: ", e);
        }

        return null;
    }
}
