package com.student.portal.Session;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  SessionController {

    private final SessionInterface sessionInterface;

    public SessionController(SessionInterface sessionInterface) {

        this.sessionInterface = sessionInterface;
    }

    @GetMapping("sessions")
//    public List<SessionDto> list() {
//
//        return sessionInterface.getallSessions();
//    }
    public List<Map<String, Object>> list() {

        return sessionInterface.getallSessions();
    }

    @GetMapping("sessionsummarybymonth")
    public List<Map<String, Object>> GetSessionSummarybyMonth() {
        return sessionInterface.getSessionSummarybyMonth();
    }

    @GetMapping("sessionsummarybyweek")
    public List<Map<String, Object>> GetSessionSummarybyWeek() {
        return sessionInterface.getSessionSummarybyWeek();
    }

    @GetMapping("sessions/{id}")
    public SessionDto findById(@PathVariable Integer id)
    {
        try {
            return sessionInterface.getSessionbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @GetMapping("sessionsbystudent/{id}")
    public List<SessionDto> getSessionsbyStudent (@PathVariable Integer id) {
        return sessionInterface.getSessionbyStudentId(id);
    }

    @PostMapping("sessions")
    public Integer PostSession(@RequestBody SessionDto sessionDto) {
        return sessionInterface.CreateSession(sessionDto);
    }

    @PatchMapping("sessions/{id}")
    public SessionDto UpdateSession(@PathVariable Integer id , @RequestBody Map<String, Object> sessionDto) {
        return sessionInterface.UpdateSessionFields(sessionDto, id);
    }

    @DeleteMapping("sessions/{id}")
    public boolean DeleteSessionbyId(@PathVariable Integer id) {
        return sessionInterface.DeleteSessionbyId(id);
    }

}
