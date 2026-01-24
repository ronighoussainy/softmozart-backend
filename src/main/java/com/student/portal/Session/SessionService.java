package com.student.portal.Session;

import com.student.portal.Helpers.EmailService;
import com.student.portal.Instructor.InstructorDto;
import com.student.portal.Instructor.InstructorInterface;
import com.student.portal.Invoice.InvoiceDto;
import com.student.portal.Student.StudentDto;
import com.student.portal.Student.StudentInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService implements SessionInterface{

    private final SessionRepository repo;

    private final SessionMapper mapper;
    private StudentInterface studentInterface;
    private InstructorInterface instructorInterface ;
    private StudentDto studentDto;
    private SessionDto sessionDto;
    private EmailService emailservice;

    private Session session;
    private List<StudentDto> studentDtoList;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, StudentInterface studentInterface, InstructorInterface instructorInterface, EmailService emailservice) {
        this.repo = sessionRepository;
        this.mapper = sessionMapper;
        this.studentInterface = studentInterface;
        this.instructorInterface = instructorInterface;
        this.emailservice = emailservice;
    }

    public List<Map<String, Object>> getallSessions(){
        return repo.getAllSessions();

//        return repo.findAll()
//                .stream()
//                .map(this::convertSessionEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public SessionDto getSessionbyId(Integer id){
        Optional<Session> session = repo.findById(id);
        return convertSessionEntitytoDTO(session.get());
    }

    public List<SessionDto> getSessionbyStudentId(Integer id){
        return repo.getSessionbyStudentId(id)
                .stream()
                .map(this::convertSessionEntitytoDTO)
                .collect(Collectors.toList());
    }

    private SessionDto updateStudentstoDto(SessionDto sessionDto){
        if (sessionDto.getStudents()!=null)
            sessionDto.setSelectedStudents(sessionDto.getStudents().split(","));
        return sessionDto;
    }
    private SessionDto convertSessionEntitytoDTO(Session session){
        SessionDto sessionDto = mapper.sessionToSessionDto(session);
        StudentDto studentDto = studentInterface.getStudentbyId(sessionDto.getStudentId());
        sessionDto.setStudentName(studentDto.getFullName());
        InstructorDto instructorDto = instructorInterface.getInstructorbyId(sessionDto.getInstructorId());
        sessionDto.setInstructorName(instructorDto.getFullName());
        if (sessionDto.getStudents()!=null)
            sessionDto.setSelectedStudents(sessionDto.getStudents().split(","));

        return sessionDto;
    }

    public List<Map<String, Object>> getSessionSummarybyMonth(){
        return repo.getSessionSummarybyMonth();
    }

    public List<Map<String, Object>> getSessionSummarybyWeek(){
        return repo.getSessionSummarybyWeek();
    }

    public Integer CreateSession(SessionDto sessionDto){
        Session session = mapper.sessionDtoToSession(sessionDto);
        repo.saveAndFlush(session);

        Integer sessionID = repo.getMaxSessionId();
        return sessionID;
    }

    public boolean DeleteSessionbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public SessionDto UpdateSessionFields(Map<String, Object> sessionDto, Integer id){
        Session session = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("session not found on :: "+ id));

        if (sessionDto.containsKey("instructorId") )
            session.setInstructorId(Integer.parseInt(sessionDto.get("instructorId").toString()));
        if (sessionDto.containsKey("studentId") )
            session.setStudentId(Integer.parseInt(sessionDto.get("studentId").toString()));
        if (sessionDto.containsKey("duration") )
            session.setDuration(Integer.parseInt(sessionDto.get("duration").toString()));
        if (sessionDto.containsKey("notes") )
            session.setNotes(sessionDto.get("notes").toString());
        if (sessionDto.containsKey("todo") )
            session.setTodo(sessionDto.get("todo").toString());
        if (sessionDto.containsKey("students") )
            session.setStudents(sessionDto.get("students").toString());
        if (sessionDto.containsKey("type") )
            session.setType(sessionDto.get("type").toString());
        if (sessionDto.containsKey("date") && sessionDto.get("date").toString().length()!=0)
            session.setDate(LocalDate.parse(sessionDto.get("date").toString()));
        if (sessionDto.containsKey("time") && sessionDto.get("time").toString().length()!=0)
            session.setTime(sessionDto.get("time").toString());
         repo.saveAndFlush(session);
        return convertSessionEntitytoDTO(session);
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void NewSessions() {
        List<SessionDto> sessions = repo.getSessionsAdded().stream()
                .map(this::convertSessionEntitytoDTO)
                .collect(Collectors.toList());

        for (SessionDto session : sessions) {
            try {
                this.emailservice.SendSessionAddedEmail(session);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}