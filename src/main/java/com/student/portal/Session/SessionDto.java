package com.student.portal.Session;

import com.student.portal.Student.Student;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
public class SessionDto implements Serializable {
    private  Integer id;
    private  Integer studentId;
    private  Integer instructorId;
    private String type;
    private  String notes;
    private  String todo;
    private  LocalDate date;
    private String time;
    private Integer duration;
    private String studentName;
    private String instructorName;
    private String students;
    private String[] selectedStudents;
}