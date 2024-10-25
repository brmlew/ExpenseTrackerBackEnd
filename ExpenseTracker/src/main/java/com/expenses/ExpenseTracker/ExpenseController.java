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
    
    private FieldNames fieldNames;
    
    // receive all expenses from the database
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return new ResponseEntity<List<Expense>>(expenseService.getAllExpenses(), HttpStatus.OK);
    }
   
    // add, remove, or update an expense to the database, handling the post request
    @PostMapping
    public ResponseEntity<Expense>handleExpense(@RequestBody Map<String, String> payload) {
    	if (payload.get(fieldNames.type).equals(fieldNames.add)) {
    		return new ResponseEntity<Expense>(expenseService.createExpense(payload.get(fieldNames.date),
                    new BigDecimal(payload.get(fieldNames.amount)),
                    payload.get(fieldNames.note),
                    payload.get(fieldNames.category),
                    payload.get(fieldNames.subcategory)),
                    HttpStatus.CREATED);
    	} else if (payload.get(fieldNames.type).equals(fieldNames.delete)) {
    		ObjectId id = new ObjectId(payload.get(fieldNames.id));
    		return new ResponseEntity<Expense>(expenseService.deleteExpense(id), 
    				HttpStatus.OK);
    	} else {
    		ObjectId id = new ObjectId(payload.get(fieldNames.id));
    		return new ResponseEntity<Expense>(expenseService.updateExpense(id,
    				payload.get(fieldNames.date),
                    new BigDecimal(payload.get(fieldNames.amount)),
                    payload.get(fieldNames.note),
                    payload.get(fieldNames.category),
                    payload.get(fieldNames.subcategory)),
    				HttpStatus.CREATED);
    	}
        
    }
}
