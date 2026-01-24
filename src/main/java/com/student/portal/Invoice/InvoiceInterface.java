package com.student.portal.Invoice;

import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;

public interface InvoiceInterface {
    public List<InvoiceDto> getallInvoices();

    public InvoiceDto getInvoicebyId(Integer id);

    public List<InvoiceDto> getInvoicebyStudentId(Integer id);

    public List<Map<String, Object>> getInvoiceSummarybyMonth();

    public List<Map<String, Object>> getRevenueSummarybyMonth();

//    public List<InvoiceDto> getInvoicebyFilter(Map<String, Object> invoiceDto);

    public List<Map<String, Object>> getInvoicebyFilter(Map<String, Object> invoiceDto);

    public Integer CreateInvoice(InvoiceDto invoiceDto);

    public boolean DeleteInvoicebyId(Integer id);

    public InvoiceDto UpdateInvoiceFields(Map<String, Object> invoiceDto, Integer id);

    public Integer GenerateInvoices(String monthofyear);

}