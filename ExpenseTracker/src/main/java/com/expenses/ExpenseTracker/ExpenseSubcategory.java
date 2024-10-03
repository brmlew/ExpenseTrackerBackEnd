package com.expenses.ExpenseTracker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "expense_subcategories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSubcategory {
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId category;
    private String subcategoryName;

    public ExpenseSubcategory(ObjectId category, String subcategoryName) {
        this.category = category;
        this.setSubcategoryName(subcategoryName);
    }
    
    public ObjectId getId() {
    	return this.id;
    }

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
}
