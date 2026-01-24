package com.student.portal.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query(value = "SELECT max(id) FROM Expense ")
    Integer getMaxExpenseId();

    @Query(value = " select  exp.id id,\n" +
            "    exp.amount amount,\n" +
            "   exp.date date,\n" +
            "    exp.paidto paidto,\n" +
            "   exp.type type,\n" +
            "    exp.notes notes,\n" +
            "    l.name labName,\n" +
            "    exp.labID labID\n" +
            "            from expenses exp, labs l\n" +
            "            where exp.labid = l.id ", nativeQuery = true)
    List<Map<String, Object>> getAllExpenses();
}