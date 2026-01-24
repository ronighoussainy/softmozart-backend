package com.student.portal.Session;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sessiondetail")
public class SessionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "studentId", nullable = false)
    private Integer studentId;

    @Column(name = "sessionId", nullable = false)
    private Integer sessionId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "todo")
    private String todo;

}