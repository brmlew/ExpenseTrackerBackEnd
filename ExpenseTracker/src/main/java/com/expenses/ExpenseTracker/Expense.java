package com.expenses.ExpenseTracker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.util.Date;

@Document(collection = "expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
    private Date date;
    private Float amount;
    private String note;
    private ExpenseCategory category;
    private ExpenseSubcategory subcategory;

    public Expense(Date date, Float amount, String note, ExpenseCategory category, ExpenseSubcategory subcategory) {
        this.setDate(date);
        this.setAmount(amount);
        this.setNote(note);
        this.setCategory(category);
        this.setSubcategory(subcategory);
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ExpenseCategory getCategory() {
		return category;
	}

	public void setCategory(ExpenseCategory category) {
		this.category = category;
	}

	public ExpenseSubcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(ExpenseSubcategory subcategory) {
		this.subcategory = subcategory;
	}
}
