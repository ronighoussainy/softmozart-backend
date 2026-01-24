package com.student.portal.Invoice;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class InvoiceController {

    private final InvoiceInterface invoiceInterface;

    public InvoiceController(InvoiceInterface invoiceInterface) {

        this.invoiceInterface = invoiceInterface;
    }

    @PostMapping("invoices/get/")
    public Page<InvoiceDto> searchInvoices(
            @RequestBody Map<String, Object> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return invoiceInterface.getInvoices(filters, page, size);
    }


    @GetMapping("invoicesummary")
    public List<Map<String, Object>> GetInvoiceSummary() {

        return invoiceInterface.getInvoiceSummarybyMonth();
    }

    @GetMapping("revenuesummary")
    public List<Map<String, Object>> GetRevenueSummary() {

        return invoiceInterface.getRevenueSummarybyMonth();
    }

    @GetMapping("invoices/{id}")
    public InvoiceDto findById(@PathVariable Integer id) {
        try {
            return invoiceInterface.getInvoicebyId(id);
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    @PostMapping("invoicesfilter")
    public List<Map<String, Object>> getInvoicesbyFilter(@RequestBody Map<String, Object> invoiceDto) {
        return invoiceInterface.getInvoicebyFilter(invoiceDto);

//        try {
//            return invoiceInterface.getInvoicebyFilter(invoiceDto);
//        }
//        catch (Exception e) {
//            System.out.println("Error "+ e);
//            return null;
//        }
    }

    @GetMapping("invoicesbystudent/{id}")
    public List<InvoiceDto> getInvoicesbyStudent(@PathVariable Integer id) {
        return invoiceInterface.getInvoicebyStudentId(id);
    }

    @PostMapping("invoices")
    public Integer PostInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceInterface.CreateInvoice(invoiceDto);
    }

    @PatchMapping("invoices/{id}")
    public InvoiceDto UpdateInvoice(@PathVariable Integer id, @RequestBody Map<String, Object> invoiceDto) {
        return invoiceInterface.UpdateInvoiceFields(invoiceDto, id);
    }

    @DeleteMapping("invoices/{id}")
    public boolean DeleteInvoicebyId(@PathVariable Integer id) {
        return invoiceInterface.DeleteInvoicebyId(id);
    }

    @PostMapping("createinvoices/{month}")
    public Integer CreateInvoice(@PathVariable String month) {
        return invoiceInterface.GenerateInvoices(month);
    }
}
