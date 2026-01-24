package com.student.portal.Instructor;

import lombok.Data;

import java.io.Serializable;

@Data
public class InstructorDto implements Serializable {
    private  Integer id;
    private  String firstName;
    private  String lastName;
    private  byte[] photo;
    private  String email;
    private  String phone;
    private  String address;
    private  String fullName;
}
