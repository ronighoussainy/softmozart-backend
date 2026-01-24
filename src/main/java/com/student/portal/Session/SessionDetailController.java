package com.student.portal.Session;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  SessionDetailController {

    private final SessionDetailInterface sessionDetailInterface;

    public SessionDetailController(SessionDetailInterface sessionDetailInterface) {

        this.sessionDetailInterface = sessionDetailInterface;
    }

    @GetMapping("sessiondetails")
    public List<Map<String, Object>> list() {

        return sessionDetailInterface.getallSessionDetails();
    }

    @GetMapping("sessiondetails/{id}")
    public SessionDetailDto findById(@PathVariable Integer id)
    {
        try {
            return sessionDetailInterface.getSessionDetailbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @GetMapping("sessiondetailsbystudent/{id}")
    public List<Map<String, Object>> getSessionDetailsbyStudent (@PathVariable Integer id) {
        return sessionDetailInterface.getSessionDetailbyStudentId(id);
    }

    @GetMapping("sessiondetailsbysession/{id}")
    public List<Map<String, Object>> getSessionDetailsbySession (@PathVariable Integer id) {
        return sessionDetailInterface.getSessionDetailbySessionId(id);
    }

    @PostMapping("sessiondetails")
    public Integer PostSessionDetail(@RequestBody SessionDetailDto sessionDetailDto) {
        return sessionDetailInterface.CreateSessionDetail(sessionDetailDto);
    }

    @PatchMapping("sessiondetails/{id}")
    public SessionDetailDto UpdateSessionDetail(@PathVariable Integer id , @RequestBody Map<String, Object> sessionDetailDto) {
        return sessionDetailInterface.UpdateSessionDetailFields(sessionDetailDto, id);
    }

    @DeleteMapping("sessiondetails/{id}")
    public boolean DeleteSessionDetailbyId(@PathVariable Integer id) {
        return sessionDetailInterface.DeleteSessionDetailbyId(id);
    }

}
