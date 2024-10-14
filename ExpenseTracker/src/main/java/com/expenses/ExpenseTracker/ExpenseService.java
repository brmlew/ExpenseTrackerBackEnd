package com.expenses.ExpenseTracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    
    public Expense deleteExpense(String date, BigDecimal amount, String note, String category, String subcategory) {
    	ExpenseCategory expenseCategory= categoryRepository.findByCategoryName(category);
        ExpenseSubcategory expenseSubcategory = subcategoryRepository.findBySubcategoryNameAndCategory(subcategory, expenseCategory.getId());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
        	Date newDate = format.parse(date);
        	System.out.println(amount);
        	System.out.println(note);
        	System.out.println(category);
        	System.out.println(subcategory);
        	
        	Expense expense = expenseRepository.findByDateAndAmountAndNoteAndCategoryIdAndSubcategoryId(newDate, amount, note, expenseCategory.getId(), expenseSubcategory.getId());
        	mongoTemplate.remove(expense);
        	return expense;
        	
        } catch (ParseException e) {
            System.out.printf("e: ", e);
        }
        return null;
    }
    
    public Expense updateExpense(String date, BigDecimal amount, String note, String category, String subcategory, String newDate, BigDecimal newAmount, String newNote, String newCategory, String newSubcategory) {
    	ExpenseCategory expenseCategory = categoryRepository.findByCategoryName(category);
        ExpenseSubcategory expenseSubcategory = subcategoryRepository.findBySubcategoryNameAndCategory(subcategory, expenseCategory.getId());
        
        ExpenseCategory newExpenseCategory = categoryRepository.findByCategoryName(newCategory);
        ExpenseSubcategory newExpenseSubcategory = subcategoryRepository.findBySubcategoryNameAndCategory(newSubcategory, newExpenseCategory.getId());
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
        	Date oldDate = format.parse(date);
        	Date newDateParsed = format.parse(newDate);
        	Query query = new Query();
        	query.addCriteria(new Criteria().andOperator(Criteria.where("date").is(oldDate),
        			Criteria.where("amount").is(amount),
        			Criteria.where("note").is(note),
        			Criteria.where("category").is(expenseCategory),
        			Criteria.where("subcategory").is(expenseSubcategory)));
        			
        	Update update = new Update();
        	update.set("date", newDateParsed);
        	update.set("amount", newAmount);
        	update.set("note", newNote);
        	update.set("category", newExpenseCategory);
        	update.set("subcategory", newExpenseSubcategory);
        	
        	mongoTemplate.updateFirst(query, update, Expense.class);
        	return expenseRepository.findByDateAndAmountAndNoteAndCategoryIdAndSubcategoryId(newDateParsed, newAmount, newNote, newExpenseCategory.getId(), newExpenseSubcategory.getId());
        	
        } catch (ParseException e) {
            System.out.printf("e: ", e);
        }
        
        return null;
    }
    
    
}
