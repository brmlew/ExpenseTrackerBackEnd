package com.expenses.ExpenseTracker;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpenseRepository extends MongoRepository<Expense, ObjectId> {
	Expense findByDateAndAmountAndNoteAndCategoryIdAndSubcategoryId(Date date, BigDecimal Amount, String note, ObjectId category, ObjectId subcategory);
}
