package com.student.portal.Instructor;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "`FirstName`", nullable = false, length = 50)
    private String firstName;

    @Column(name = "`LastName`", length = 50)
    private String lastName;

    @Column(name = "Photo")
    private byte[] photo;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 15)
    private String phone;

    @Lob
    @Column(name = "Address", nullable = false)
    private String address;
}