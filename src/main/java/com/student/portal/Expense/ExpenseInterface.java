package com.student.portal.Expense;

import java.util.List;
import java.util.Map;

public interface ExpenseInterface {
    public List<Map<String, Object>> getallExpenses();

    public ExpenseDto getExpensebyId(Integer id);

    public Integer CreateExpense(ExpenseDto expenseDto);

    public boolean DeleteExpensebyId(Integer id);

    public ExpenseDto UpdateExpenseFields(Map<String, Object> expenseDto, Integer id);


}