package com.student.portal.Session;

import java.util.List;
import java.util.Map;

public interface SessionInterface {
//    public List<SessionDto> getallSessions();
    public List<Map<String, Object>> getallSessions();

    public SessionDto getSessionbyId(Integer id);

    public List<SessionDto> getSessionbyStudentId(Integer id);

    public Integer CreateSession(SessionDto sessionDto);

    public List<Map<String, Object>> getSessionSummarybyMonth();

    public List<Map<String, Object>> getSessionSummarybyWeek();

    public boolean DeleteSessionbyId(Integer id);

    public SessionDto UpdateSessionFields(Map<String, Object> sessionDto, Integer id);
}
