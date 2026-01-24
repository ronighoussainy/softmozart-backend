package com.student.portal.License;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "licenses")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "studentId", nullable = false)
    private Integer studentId;

    @Column(name = "licensekey", nullable = false, length = 100)
    private String licensekey;

    @Column(name = "regid", nullable = false, length = 1000)
    private String regid;

    @Column(name = "purchasedate", nullable = false)
    private LocalDate purchasedate;

    @Column(name = "expirydate", nullable = false)
    private LocalDate expirydate;

    @Column(name = "amount", nullable = false, precision = 10, scale = 5)
    private Double amount;

}