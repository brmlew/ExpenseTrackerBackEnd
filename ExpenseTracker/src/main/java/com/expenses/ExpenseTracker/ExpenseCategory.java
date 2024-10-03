package com.expenses.ExpenseTracker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "expense_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategory {
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
    private String categoryName;

    public ExpenseCategory(String categoryName) {
        this.setCategoryName(categoryName);
    }
    
    public ObjectId getId() {
    	return this.id;
    }
    
    public void setId(ObjectId id) {
    	this.id = id;
    }

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
