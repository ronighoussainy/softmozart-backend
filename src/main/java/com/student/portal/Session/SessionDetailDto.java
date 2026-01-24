package com.student.portal.Session;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SessionDetailDto implements Serializable {
    private  Integer id;
    private  Integer studentId;
    private  Integer sessionId;
    private  String notes;
    private  String todo;
    private LocalDate date;
    private String time;
    private Integer duration;
    private String studentName;
    private String instructorName;
}