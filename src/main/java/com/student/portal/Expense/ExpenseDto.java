package com.student.portal.Expense;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ExpenseDto implements Serializable {
    private  Integer id;
    private  Double amount;
    private LocalDate date;
    private  String paidto;
    private String type;
    private String notes;
    private String labName;
    private Integer labID;
}
