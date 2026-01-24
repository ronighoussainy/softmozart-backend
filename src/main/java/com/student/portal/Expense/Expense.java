package com.student.portal.Expense;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "amount", nullable = false, precision = 10, scale = 5)
    private Double amount;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "paidto", nullable = true)
    private String paidto;

    @Column(name = "notes", nullable = true, length = 500)
    private String notes;

    @Column(name = "labID")
    private Integer labID;

}