package com.student.portal.License;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class LicenseDto implements Serializable {
    private  Integer id;
    private  Integer studentId;
    private  String licensekey;
    private  String regid;
    private  LocalDate purchasedate;
    private  LocalDate expirydate;
    private  String studentName;
    private Double amount;
    private String status;
    private long remainingdays;
}
