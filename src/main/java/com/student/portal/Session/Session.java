package com.student.portal.Session;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "studentId", nullable = false)
    private Integer studentId;

    @Column(name = "instructorId", nullable = false)
    private Integer instructorId;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "students")
    private String students;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "notes")
    private String notes;

    @Column(name = "todo")
    private String todo;

}