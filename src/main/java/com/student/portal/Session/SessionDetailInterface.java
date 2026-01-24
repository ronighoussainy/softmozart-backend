package com.student.portal.Session;

import java.util.List;
import java.util.Map;

public interface SessionDetailInterface {
    public List<Map<String, Object>> getallSessionDetails();

    public SessionDetailDto getSessionDetailbyId(Integer id);

    public List<Map<String, Object>> getSessionDetailbyStudentId(Integer id);

    public List<Map<String, Object>> getSessionDetailbySessionId(Integer id);

    public Integer CreateSessionDetail(SessionDetailDto sessionDetailDto);

    public boolean DeleteSessionDetailbyId(Integer id);

    public SessionDetailDto UpdateSessionDetailFields(Map<String, Object> sessionDetailDto, Integer id);
}
