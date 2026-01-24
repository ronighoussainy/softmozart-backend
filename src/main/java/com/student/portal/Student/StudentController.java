package com.student.portal.Student;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  StudentController {

    private final StudentInterface studentInterface;

    public StudentController(StudentInterface studentInterface) {

        this.studentInterface = studentInterface;
    }

    @GetMapping("students")
    public List<Map<String, Object>> list() {

        return studentInterface.getallStudents();
    }

    @GetMapping("studentssummary")
    public List<Map<String, Object>> getStudentsSummary() {
        return studentInterface.getStudentsSummary();
    }

    @GetMapping("activestudentssummary")
    public List<Map<String, Object>> getActiveStudentsSummary() {
        return studentInterface.getActiveStudentsSummary();
    }

    @GetMapping("students/{id}")
    public StudentDto findById(@PathVariable Integer id)
    {
        try {
            return studentInterface.getStudentbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("students")
    public Integer PostStudent(@RequestBody StudentDto studentDto) {
        return studentInterface.CreateStudent(studentDto);
    }

    @PatchMapping ("students/{id}")
    public StudentDto UpdateStudent(@PathVariable Integer id ,@RequestBody Map<String, Object> studentDto) {
        return studentInterface.UpdateStudentFields(studentDto, id);
    }

    @DeleteMapping("students/{id}")
    public boolean DeleteStudentbyId(@PathVariable Integer id) {

        return studentInterface.DeleteStudentbyId(id);
    }

}
