package com.student.portal.User;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDto implements Serializable {
    private  Integer id;
    private  String username;
    private  String password;
    private  LocalDate dateupdated;
    private  String role;
    private  Integer studentId;
    private  String studentName;
}
