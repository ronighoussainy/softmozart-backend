package com.student.portal.Invoice;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "monthofyear", nullable = false, length = 50)
    private String monthofyear;

    @Column(name = "amount", nullable = false, precision = 10, scale = 5)
    private Double amount;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "note", nullable = true, length = 500)
    private String note;

    @Column(name = "instructor_id", nullable = false)
    private Integer instructorId;

    @Column(name = "dateofpayment", nullable = true)
    private LocalDate dateofpayment;

}