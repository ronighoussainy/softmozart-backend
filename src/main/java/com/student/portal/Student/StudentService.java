package com.student.portal.Student;

import com.student.portal.Helpers.EmailService;
import com.student.portal.Instructor.InstructorDto;
import com.student.portal.Instructor.InstructorInterface;
import com.student.portal.Others.LabDto;
import com.student.portal.Others.LabInterface;
import com.student.portal.Others.LevelDto;
import com.student.portal.Others.LevelInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentInterface{

    private final StudentRepository repo;
    private final StudentMapper mapper;
    private InstructorInterface instructorInterface;
    private InstructorDto instructorDto;
    private LabInterface labInterface;
    private LabDto labDto;
    private LevelInterface levelInterface;
    private LevelDto levelDto;
    private EmailService emailservice;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, InstructorInterface instructorInterface, LabInterface labInterface, LevelInterface levelInterface, EmailService emailservice) {
        this.repo = studentRepository;
        this.mapper = studentMapper;
        this.instructorInterface = instructorInterface;
        this.labInterface = labInterface;
        this.levelInterface = levelInterface;
        this.emailservice = emailservice;
    }

    public List<Map<String, Object>> getallStudents(){
        return repo.getAllStudents();

    }

    public List<StudentDto> getallStudentsOld(){

        return repo.findAll()
                .stream()
                .map(this::convertStudentEntitytoDTO)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentbyId(Integer id){
        Optional<Student> student = repo.findById(id);
        if( student.isPresent() ) {
            return convertStudentEntitytoDTO(student.get());
        }
        else
            return new StudentDto();
    }

    public List<Map<String, Object>> getStudentsSummary(){

        return repo.getStudentsSummary();
    }

    public List<Map<String, Object>> getActiveStudentsSummary(){
        return repo.getActiveStudentsSummary();
    }

    private StudentDto convertStudentEntitytoDTO(Student student){
        StudentDto studentDto = mapper.studentToStudentDto(student);
//        studentDto.setFullName(studentDto.getFirstName()+' '+studentDto.getMiddleName()+' '+studentDto.getLastName());
        //Get Instructors Name
        instructorDto = instructorInterface.getInstructorbyId(student.getInstructorID());
        studentDto.setInstructorName(instructorDto.getFullName());

        //Get Level's Title
        levelDto = levelInterface.getLevelbyId(student.getLevelID());
        studentDto.setLevelName(levelDto.getTitle());

        //Get Lab's Name
        labDto = labInterface.getLabbyId(student.getLabID());
        studentDto.setLabName(labDto.getName());
        //Calculate Age
        Period p =Period.between(studentDto.getDob(), LocalDate.now());
        studentDto.setAge(p.getYears() +" Years, " + p.getMonths()+ " Months");

        return studentDto;
    }

    public Integer CreateStudent(StudentDto studentDto){
        Student student = mapper.studentDtoToStudent(studentDto);
        student.setFullName(student.getFirstName()+' '+student.getMiddleName()+' '+student.getLastName());
        repo.saveAndFlush(student);
        return repo.getMaxStudentId();
    }

    public boolean DeleteStudentbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public StudentDto UpdateStudentFields(Map<String, Object> studentDto, Integer id){
        Student student = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("student not found on :: "+ id));

        if (studentDto.containsKey("firstName") )
            student.setFirstName(studentDto.get("firstName").toString());
        if (studentDto.containsKey("middleName") )
            student.setMiddleName(studentDto.get("middleName").toString());
        if (studentDto.containsKey("lastName") )
            student.setLastName(studentDto.get("lastName").toString());
        if (studentDto.containsKey("mothersName") )
            student.setMothersName(studentDto.get("mothersName").toString());
        if (studentDto.containsKey("email") )
            student.setEmail(studentDto.get("email").toString());
        if (studentDto.containsKey("phone") )
            student.setPhone(studentDto.get("phone").toString());
        if (studentDto.containsKey("address") )
            student.setAddress(studentDto.get("address").toString());
        if (studentDto.containsKey("instructorID"))
            student.setInstructorID(Integer.parseInt(studentDto.get("instructorID").toString()));
        if (studentDto.containsKey("levelID"))
            student.setLevelID(Integer.parseInt(studentDto.get("levelID").toString()));
        if (studentDto.containsKey("labID"))
            student.setLabID(Integer.parseInt(studentDto.get("labID").toString()));
        if (studentDto.containsKey("billing") )
            student.setBilling(studentDto.get("billing").toString());
        if (studentDto.containsKey("fees") )
            student.setFees(Double.valueOf(studentDto.get("fees").toString()));
        if (studentDto.containsKey("sessionsperweek"))
            student.setSessionsperweek(Integer.parseInt(studentDto.get("sessionsperweek").toString()));
        if (studentDto.containsKey("status") )
            student.setStatus(studentDto.get("status").toString());
        if (studentDto.containsKey("residence") )
            student.setResidence(studentDto.get("residence").toString());
        if (studentDto.containsKey("nationality") )
            student.setNationality(studentDto.get("nationality").toString());
        if (studentDto.containsKey("dob") )
            student.setDob(LocalDate.parse(studentDto.get("dob").toString()));
        if (studentDto.containsKey("photo") )
            student.setPhoto(studentDto.get("photo").toString());
        if (studentDto.containsKey("facebook") )
            student.setFacebook(studentDto.get("facebook").toString());
        if (studentDto.containsKey("youtube") )
            student.setYoutube(studentDto.get("youtube").toString());
        if (studentDto.containsKey("instagram") )
            student.setInstagram(studentDto.get("instagram").toString());
        student.setFullName(student.getFirstName()+' '+student.getMiddleName()+' '+student.getLastName());
        repo.saveAndFlush(student);
        return convertStudentEntitytoDTO(student);
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void BirthdaysAlert() {
        List<StudentDto> students = repo.getStudentBirthdays().stream()
                .map(this::convertStudentEntitytoDTO)
                .collect(Collectors.toList());

        for (StudentDto student : students) {
            try {
                this.emailservice.SendEBirthdayEmail(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
