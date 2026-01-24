package com.student.portal.Session;

import com.student.portal.Helpers.EmailService;
import com.student.portal.Instructor.InstructorInterface;
import com.student.portal.Student.StudentDto;
import com.student.portal.Student.StudentInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionDetailService implements SessionDetailInterface{

    private final SessionDetailRepository repo;
    private final SessionRepository sessionRepository;
    private final SessionDetailMapper mapper;
    private final SessionMapper sessionMapper;
    private StudentInterface studentInterface;
    private InstructorInterface instructorInterface ;
    private SessionInterface sessionInterface;
    private StudentDto studentDto;
    private SessionDetailDto sessionDetailDto;
    private EmailService emailservice;

    private SessionDetail sessionDetail;
    private List<StudentDto> studentDtoList;

    public SessionDetailService(SessionDetailRepository sessionDetailRepository, SessionRepository sessionRepository, SessionDetailMapper sessionDetailMapper, SessionMapper sessionMapper, StudentInterface studentInterface, InstructorInterface instructorInterface, SessionInterface sessionInterface, EmailService emailservice) {
        this.repo = sessionDetailRepository;
        this.sessionRepository = sessionRepository;
        this.mapper = sessionDetailMapper;
        this.sessionMapper = sessionMapper;
        this.studentInterface = studentInterface;
        this.instructorInterface = instructorInterface;
        this.sessionInterface = sessionInterface;
        this.emailservice = emailservice;
    }

    public List<Map<String, Object>> getallSessionDetails(){
        return repo.getAllSessionDetails();
        //        return repo.findAll()
//                .stream()
//                .map(this::convertSessionDetailEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public SessionDetailDto getSessionDetailbyId(Integer id){
        Optional<SessionDetail> sessionDetail = repo.findById(id);
        return convertSessionDetailEntitytoDTO(sessionDetail.get());
    }

    public List<Map<String, Object>>  getSessionDetailbyStudentId(Integer id){

        return repo.getSessionDetailbyStudentId(id);

//        return repo.getSessionDetailbyStudentId(id)
//                .stream()
//                .map(this::convertSessionDetailEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public List<Map<String, Object>>  getSessionDetailbySessionId(Integer id){

         return repo.getSessionDetailbySessionId(id);
//        return repo.getSessionDetailbySessionId(id)
//                .stream()
//                .map(this::convertSessionDetailEntitytoDTO)
//                .collect(Collectors.toList());
    }

    private SessionDetailDto convertSessionDetailEntitytoDTO(SessionDetail sessionDetail){
        SessionDetailDto sessionDetailDto = mapper.sessionDetailToSessionDetailDto(sessionDetail);
        SessionDto sessionDto = sessionInterface.getSessionbyId(sessionDetailDto.getSessionId());
        sessionDetailDto.setInstructorName(sessionDto.getInstructorName());
        StudentDto studentDto = studentInterface.getStudentbyId(sessionDetailDto.getStudentId());
        sessionDetailDto.setStudentName(studentDto.getFullName());
        sessionDetailDto.setDate(sessionDto.getDate());
        sessionDetailDto.setTime(sessionDto.getTime());
        sessionDetailDto.setDuration(sessionDto.getDuration());
        return sessionDetailDto;
    }

    public Integer CreateSessionDetail(SessionDetailDto sessionDetailDto){
        SessionDetail sessionDetail = mapper.sessionDetailDtoToSessionDetail(sessionDetailDto);
        repo.saveAndFlush(sessionDetail);
        this.UpdateSession(sessionDetailDto.getSessionId());

        Integer sessionID = repo.getMaxSessionDetailId();
        //Send session added email
//        try {
//            SessionDetailDto sessionDetailDto2 = this.getSessionDetailbyId(sessionID);
//            this.emailservice.SendSessionDetailAddedEmail(sessionDetailDto2);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        return repo.getMaxSessionDetailId();
    }

    public void UpdateSession(Integer id){
        List<String> StudentsList = repo.getStudentsbySessionId(id)
                .stream()
                .collect(Collectors.toList());
        SessionDto sessionDto = sessionInterface.getSessionbyId(id);
        Session session = sessionMapper.sessionDtoToSession(sessionDto);
        session.setStudents(StudentsList.toString());
        sessionRepository.saveAndFlush(session);
    }


    public boolean DeleteSessionDetailbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public SessionDetailDto UpdateSessionDetailFields(Map<String, Object> sessionDetailDto, Integer id){
        SessionDetail sessionDetail = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("sessionDetail not found on :: "+ id));

           if (sessionDetailDto.containsKey("studentId") )
            sessionDetail.setStudentId(Integer.parseInt(sessionDetailDto.get("studentId").toString()));
          if (sessionDetailDto.containsKey("notes") )
            sessionDetail.setNotes(sessionDetailDto.get("notes").toString());
        if (sessionDetailDto.containsKey("todo") )
            sessionDetail.setTodo(sessionDetailDto.get("todo").toString());
           repo.saveAndFlush(sessionDetail);
        this.UpdateSession(sessionDetail.getSessionId());
        return convertSessionDetailEntitytoDTO(sessionDetail);
    }

}