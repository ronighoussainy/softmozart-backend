package com.student.portal.Student;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StudentInterface {

    public List<Map<String, Object>> getallStudents();

    public List<StudentDto> getallStudentsOld();

    public StudentDto getStudentbyId(Integer id);

    public List<Map<String, Object>> getStudentsSummary();

    public List<Map<String, Object>> getActiveStudentsSummary();

    public Integer CreateStudent(StudentDto studentDto);

    public boolean DeleteStudentbyId(Integer id);

    public StudentDto UpdateStudentFields(Map<String, Object> studentDto, Integer id);


}
