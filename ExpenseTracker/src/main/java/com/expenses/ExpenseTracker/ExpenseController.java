package com.expenses.ExpenseTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   
    // add an expense to the database, handling the post request
    @PostMapping
    public ResponseEntity<Expense>createExpense(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Expense>(expenseService.createExpense(payload.get("date"),
                Float.valueOf(payload.get("amount")),
                payload.get("note"),
                payload.get("category"),
                payload.get("subcategory")),
                HttpStatus.CREATED);
    }
}
