package com.expenses.ExpenseTracker;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<ExpenseCategory, ObjectId> {
    ExpenseCategory findByCategoryName(String categoryName);
}
