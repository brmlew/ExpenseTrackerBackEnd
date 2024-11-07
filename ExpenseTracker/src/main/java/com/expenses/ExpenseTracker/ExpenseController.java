package com.expenses.ExpenseTracker;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    
    // receive all expenses from the database
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return new ResponseEntity<List<Expense>>(expenseService.getAllExpenses(), HttpStatus.OK);
    }
   
    // add, remove, or update an expense to the database, handling the post request
    @PostMapping
    public ResponseEntity<Expense>handleExpense(@RequestBody Map<String, String> payload) {
    	if (payload.get(FieldNames.TYPE).equals(FieldNames.ADD)) {
    		return new ResponseEntity<Expense>(expenseService.createExpense(payload.get(FieldNames.DATE),
                    new BigDecimal(payload.get(FieldNames.AMOUNT)),
                    payload.get(FieldNames.NOTE),
                    payload.get(FieldNames.CATEGORY),
                    payload.get(FieldNames.SUBCATEGORY)),
                    HttpStatus.CREATED);
    	} else if (payload.get(FieldNames.TYPE).equals(FieldNames.DELETE)) {
    		ObjectId id = new ObjectId(payload.get(FieldNames.ID));
    		return new ResponseEntity<Expense>(expenseService.deleteExpense(id), 
    				HttpStatus.OK);
    	} else {
    		ObjectId id = new ObjectId(payload.get(FieldNames.ID));
    		return new ResponseEntity<Expense>(expenseService.updateExpense(id,
    				payload.get(FieldNames.DATE),
                    new BigDecimal(payload.get(FieldNames.AMOUNT)),
                    payload.get(FieldNames.NOTE),
                    payload.get(FieldNames.CATEGORY),
                    payload.get(FieldNames.SUBCATEGORY)),
    				HttpStatus.CREATED);
    	}
        
    }
}
