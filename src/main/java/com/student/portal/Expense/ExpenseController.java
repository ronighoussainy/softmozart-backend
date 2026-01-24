package com.student.portal.Expense;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class ExpenseController {

    private final ExpenseInterface expenseInterface;

    public ExpenseController(ExpenseInterface expenseInterface) {

        this.expenseInterface = expenseInterface;
    }

    @GetMapping("expenses")
    public List<Map<String, Object>> list() {

        return expenseInterface.getallExpenses();
    }

    @GetMapping("expenses/{id}")
    public ExpenseDto findById(@PathVariable Integer id)
    {
        try {
            return expenseInterface.getExpensebyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("expenses")
    public Integer PostExpense(@RequestBody ExpenseDto expenseDto) {
        return expenseInterface.CreateExpense(expenseDto);
    }

    @PatchMapping ("expenses/{id}")
    public ExpenseDto UpdateExpense(@PathVariable Integer id , @RequestBody Map<String, Object> expenseDto) {
        return expenseInterface.UpdateExpenseFields(expenseDto, id);
    }

    @DeleteMapping("expenses/{id}")
    public boolean DeleteExpensebyId(@PathVariable Integer id) {
        return expenseInterface.DeleteExpensebyId(id);
    }

}
