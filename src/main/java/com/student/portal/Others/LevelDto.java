package com.student.portal.Others;

import lombok.Data;

import java.io.Serializable;

@Data
public class LevelDto implements Serializable {
    private  Integer id;
    private  String title;
    private  String description;
}
