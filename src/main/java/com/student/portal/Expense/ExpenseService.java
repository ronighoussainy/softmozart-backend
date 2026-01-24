package com.student.portal.Expense;

import com.student.portal.Helpers.EmailService;
import com.student.portal.Invoice.InvoiceDto;
import com.student.portal.Others.LabDto;
import com.student.portal.Others.LabInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService implements ExpenseInterface {

    private final ExpenseRepository repo;
    private final ExpenseMapper mapper;

    private ExpenseDto expenseDto;
    private Expense expense;
    private LabInterface labInterface;
    private LabDto labDto;
    private EmailService emailservice;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, LabInterface labInterface, EmailService emailservice) {
        this.repo = expenseRepository;
        this.mapper = expenseMapper;
        this.labInterface = labInterface;
        this.emailservice = emailservice;
    }

    public List<Map<String, Object>> getallExpenses(){
        return repo.getAllExpenses();

//        return repo.findAll()
//                .stream()
//                .map(this::convertExpenseEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public ExpenseDto getExpensebyId(Integer id){
        Optional<Expense> expense = repo.findById(id);

        return convertExpenseEntitytoDTO(expense.get());
    }

    private ExpenseDto convertExpenseEntitytoDTO(Expense expense){
        ExpenseDto expenseDto = mapper.expenseToExpenseDto(expense);
        //Get Lab's Name
//        labDto = labInterface.getLabbyId(expense.getLabID());
//        expenseDto.setLabName(labDto.getName());
        return expenseDto;
    }

    public Integer CreateExpense(ExpenseDto expenseDto){
        Expense expense = mapper.expenseDtoToExpense(expenseDto);
        repo.saveAndFlush(expense);

        ExpenseDto expenseDto2 = this.getExpensebyId(repo.getMaxExpenseId());
        try {
                this.emailservice.SendExpenseAddedEmail(expenseDto2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        return repo.getMaxExpenseId();
    }

    public boolean DeleteExpensebyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public ExpenseDto UpdateExpenseFields(Map<String, Object> expenseDto, Integer id) {
        Expense expense = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("expense not found on :: "+ id));

        if (expenseDto.containsKey("notes") )
            expense.setNotes(expenseDto.get("notes").toString());
        if (expenseDto.containsKey("type") )
            expense.setType(expenseDto.get("type").toString());
        if (expenseDto.containsKey("amount") )
            expense.setAmount(Double.valueOf(expenseDto.get("amount").toString()));
        if (expenseDto.containsKey("labID"))
            expense.setLabID(Integer.parseInt(expenseDto.get("labID").toString()));
        if (expenseDto.containsKey("paidto") )
            expense.setPaidto(expenseDto.get("paidto").toString());
        if (expenseDto.containsKey("date") && expenseDto.get("date").toString().length()!=0)
            expense.setDate(LocalDate.parse(expenseDto.get("date").toString()));
        repo.saveAndFlush(expense);

        return convertExpenseEntitytoDTO(expense);
    }


}