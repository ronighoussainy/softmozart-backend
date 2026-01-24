package com.student.portal.Others;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountryDto implements Serializable {
    private  Integer id;
    private  String name;
}
