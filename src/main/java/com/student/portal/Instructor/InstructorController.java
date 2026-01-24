package com.student.portal.Instructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  InstructorController {

    private final InstructorInterface instructorInterface;

    public InstructorController(InstructorInterface instructorInterface) {

        this.instructorInterface = instructorInterface;
    }

    @GetMapping("instructors")
    public List<InstructorDto> list() {

        return instructorInterface.getallInstructors();
    }

    @GetMapping("instructors/{id}")
    public InstructorDto findById(@PathVariable Integer id)
    {
        try {
            return instructorInterface.getInstructorbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("instructors")
    public Integer PostInstructor(@RequestBody InstructorDto instructorDto) {
        return instructorInterface.CreateInstructor(instructorDto);
    }

    @PatchMapping ("instructors/{id}")
    public InstructorDto UpdateInstructor(@PathVariable Integer id ,@RequestBody Map<String, Object> instructorDto) {
        return instructorInterface.UpdateInstructorFields(instructorDto, id);
    }

    @DeleteMapping("instructors/{id}")
    public boolean DeleteInstructorbyId(@PathVariable Integer id) {

        return instructorInterface.DeleteInstructorbyId(id);
    }

}
