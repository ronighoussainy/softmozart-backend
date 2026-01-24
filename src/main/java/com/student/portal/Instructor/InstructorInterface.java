package com.student.portal.Instructor;

import java.util.List;
import java.util.Map;

public interface InstructorInterface {

    public List<InstructorDto> getallInstructors();

    public InstructorDto getInstructorbyId(Integer id);

    public Integer CreateInstructor(InstructorDto studentDto);

    public boolean DeleteInstructorbyId(Integer id);

    public InstructorDto UpdateInstructorFields(Map<String, Object> studentDto, Integer id);

}
