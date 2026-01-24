package com.student.portal.Student;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "`FirstName`", nullable = false, length = 50)
    private String firstName;

    @Column(name = "`MiddleName`", length = 50)
    private String middleName;

    @Column(name = "`LastName`", length = 50)
    private String lastName;

    @Column(name = "`MothersName`", length = 50)
    private String mothersName;

    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

    @Column(name = "Nationality", nullable = false, length = 100)
    private String nationality;

    @Column(name = "Residence", nullable = false, length = 100)
    private String residence;

    @Column(name = "Email", nullable = true, length = 100)
    private String email;

    @Column(name = "Phone", nullable = true, length = 15)
    private String phone;

    @Lob
    @Column(name = "Address", nullable = true)
    private String address;

    @Column(name = "levelID", nullable = false)
    private Integer levelID;

    @Column(name = "InstructorID", nullable = false)
    private Integer instructorID;

    @Column(name = "Language", nullable = true, length = 100)
    private String language;

    @Column(name = "labID", nullable = false)
    private Integer labID;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "Youtube", length = 500)
    private String youtube;

    @Column(name = "Facebook", length = 500)
    private String facebook;

    @Column(name = "Instagram", length = 500)
    private String instagram;

    @Column(name = "billing", length = 100)
    private String billing;

    @Column(name = "sessionsperweek", nullable = false)
    private Integer sessionsperweek;

    @Column(name = "fees", nullable = false)
    private double fees;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "`fullname`", nullable = false, length = 50)
    private String fullName;
}