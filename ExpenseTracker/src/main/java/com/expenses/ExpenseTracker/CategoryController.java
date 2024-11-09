package com.expenses.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    // get all categories from the mongoDB database
    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
        return new ResponseEntity<List<ExpenseCategory>>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    // add a new category to the database given the post request
    @PostMapping
    public ResponseEntity<ExpenseCategory>createCategory(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<ExpenseCategory>(categoryService.createCategory(payload.get(FieldNames.CATEGORYNAME), payload.get(FieldNames.SUBCATEGORYNAME)), HttpStatus.CREATED);
    }
}
