package com.student.portal.Others;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "labs")
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "adress", nullable = false, length = 100)
    private String adress;

}