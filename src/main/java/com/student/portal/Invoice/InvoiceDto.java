package com.student.portal.Invoice;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
public class InvoiceDto implements Serializable {
    private  Integer id;
    private  Integer studentId;
    private  String monthofyear;
    private  Double amount;
    private  String status;
    private  String note;
    private  Integer instructorId;
    private LocalDate dateofpayment;
    private  String fullName;
    private String instructorName;
    private String studentEmail;
}
