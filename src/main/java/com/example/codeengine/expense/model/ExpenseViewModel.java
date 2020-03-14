package com.example.codeengine.expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseViewModel {
   private String id;
    private String expenseDate;
    private String description;
    private String categoryId;
    private String userId;
    private String location;
}
