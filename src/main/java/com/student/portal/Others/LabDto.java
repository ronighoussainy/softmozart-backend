package com.student.portal.Others;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabDto implements Serializable {
    private  Integer id;
    private  String name;
    private  String adress;
}
