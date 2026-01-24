package com.student.portal.Student;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class StudentDto implements Serializable {
    private  Integer id;
    private  String firstName;
    private  String middleName;
    private  String lastName;
    private  String mothersName;
    private LocalDate dob;
    private  String nationality;
    private  String residence;
    private  String email;
    private  String phone;
    private  String address;
    private  Integer levelID;
    private  Integer instructorID;
    private  String language;
    private  Integer labID;
    private  String photo;
    private  String youtube;
    private  String facebook;
    private  String instagram;
    private double fees;
    private Integer sessionsperweek;
    private String billing;
    private String status;
    private String fullName;
    private String instructorName;
    private String labName;
    private String levelName;
    private String age;
}
