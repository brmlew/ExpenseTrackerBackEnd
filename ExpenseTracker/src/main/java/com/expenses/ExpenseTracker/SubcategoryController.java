package com.expenses.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/subcategories")
public class SubcategoryController {
    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryRepository categoryRepository;
    
    // get all subcategories from the mongoDB database
    @GetMapping
    public ResponseEntity<List<ExpenseSubcategory>> getAllSubcategories() {
        return new ResponseEntity<List<ExpenseSubcategory>>(subcategoryService.getAllSubcategories(), HttpStatus.OK);
    }
    
    // add a new subcategory to the database handling a post request
    @PostMapping
    public ResponseEntity<ExpenseSubcategory> createSubcategory(@RequestBody Map<String, String> payload) {
        ExpenseCategory expenseCategory = categoryRepository.findByCategoryName(payload.get(FieldNames.CATEGORYNAME));
        return new ResponseEntity<ExpenseSubcategory>(subcategoryService.createSubcategory(expenseCategory.getId(), payload.get(FieldNames.SUBCATEGORYNAME)), HttpStatus.CREATED);
    }
}
