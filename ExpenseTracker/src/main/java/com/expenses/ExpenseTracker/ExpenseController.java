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
    	if (payload.get("type").equals("add")) {
    		return new ResponseEntity<Expense>(expenseService.createExpense(payload.get("date"),
                    new BigDecimal(payload.get("amount")),
                    payload.get("note"),
                    payload.get("category"),
                    payload.get("subcategory")),
                    HttpStatus.CREATED);
    	} else if (payload.get("type").equals("delete")) {
    		ObjectId id = new ObjectId(payload.get("id"));
    		return new ResponseEntity<Expense>(expenseService.deleteExpense(id), 
    				HttpStatus.OK);
    	} else {
    		ObjectId id = new ObjectId(payload.get("id"));
    		return new ResponseEntity<Expense>(expenseService.updateExpense(id,
    				payload.get("date"),
                    new BigDecimal(payload.get("amount")),
                    payload.get("note"),
                    payload.get("category"),
                    payload.get("subcategory")),
    				HttpStatus.CREATED);
    	}
        
    }
}
