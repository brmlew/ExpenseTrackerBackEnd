package com.expenses.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SubcategoryService subcategoryService;

    public List<ExpenseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public ExpenseCategory createCategory(String categoryName, String subcategoryName) {
        ExpenseCategory expenseCategory = new ExpenseCategory(categoryName);
        mongoTemplate.insert(expenseCategory);
        
        subcategoryService.createSubcategory(expenseCategory.getId(), subcategoryName);
        return expenseCategory;
    }
}
