package com.expenses.ExpenseTracker;

import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
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
    
    public Expense deleteExpense(ObjectId id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
    	mongoTemplate.remove(expense);
        return expense;
    }
    
    public Expense updateExpense(ObjectId id, String date, BigDecimal amount, String note, String category, String subcategory) {
    	ExpenseCategory expenseCategory = categoryRepository.findByCategoryName(category);
        ExpenseSubcategory expenseSubcategory = subcategoryRepository.findBySubcategoryNameAndCategory(subcategory, expenseCategory.getId());
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
        	Date newDate = format.parse(date);
        	Query query = new Query();
        	query.addCriteria(new Criteria().andOperator(Criteria.where(FieldNames.Id).is(id)));
        			
        	Update update = new Update();
        	update.set(FieldNames.Date, newDate);
        	update.set(FieldNames.Amount, amount);
        	update.set(FieldNames.Note, note);
        	update.set(FieldNames.Category, expenseCategory);
        	update.set(FieldNames.Subcategory, expenseSubcategory);
        	
        	mongoTemplate.updateFirst(query, update, Expense.class);
        	return expenseRepository.findById(id).orElse(null);
        	
        } catch (ParseException e) {
            System.out.printf("e: ", e);
        }
        
        return null;
    }
    
    
}
