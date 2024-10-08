package com.expenses.ExpenseTracker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
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
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal amount;
    private String note;
    private ExpenseCategory category;
    private ExpenseSubcategory subcategory;

    public Expense(Date date, BigDecimal amount, String note, ExpenseCategory category, ExpenseSubcategory subcategory) {
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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
