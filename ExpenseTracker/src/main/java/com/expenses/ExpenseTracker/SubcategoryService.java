package com.expenses.ExpenseTracker;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ExpenseSubcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public ExpenseSubcategory createSubcategory(ObjectId category, String subcategoryName) {
        ExpenseSubcategory expenseSubcategory = new ExpenseSubcategory(category, subcategoryName);

        mongoTemplate.save(expenseSubcategory);
        return expenseSubcategory;
    }
}
