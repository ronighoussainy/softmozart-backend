package com.student.portal.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SessionDetailRepository extends JpaRepository<SessionDetail, Integer> {
    @Query(value = "SELECT max(id) FROM SessionDetail")
    Integer getMaxSessionDetailId();

//    @Query(value = "SELECT u FROM SessionDetail u where u.sessionId=?1")
//    List<SessionDetail> getSessionDetailbySessionId(Integer sessionId);

    @Query(value = "SELECT concat(s.firstName,' ',s.middleName,' ',s.lastName) FROM SessionDetail u, Student s where u.sessionId=?1 and u.studentId=s.id")
    List<String> getStudentsbySessionId(Integer sessionId);

//    @Query(value = "SELECT u FROM SessionDetail u where u.studentId=?1")
//    List<SessionDetail> getSessionDetailbyStudentId(Integer studentId);

    @Query(value = " select sd.id id,\n" +
            "    sd.studentid studentId,\n" +
            "    sd.sessionid sessionId,\n" +
            "    sd.notes notes,\n" +
            "    sd.todo todo,\n" +
            "    s.date date,\n" +
            "    s.time time,\n" +
            "    s.duration duration,\n" +
            "    st.fullname studentName,\n" +
            "    i.fullname instructorName\n" +
            "    from sessiondetail sd, sessions s, students st, instructors i\n" +
            "    where s.id=sd.sessionid and sd.studentid = st.id and s.instructorid=i.id", nativeQuery = true)
    List<Map<String, Object>> getAllSessionDetails();

    @Query(value = " select sd.id id,\n" +
            "    sd.studentid studentId,\n" +
            "    sd.sessionid sessionId,\n" +
            "    sd.notes notes,\n" +
            "    sd.todo todo,\n" +
            "    s.date date,\n" +
            "    s.time time,\n" +
            "    s.duration duration,\n" +
            "    st.fullname studentName,\n" +
            "    i.fullname instructorName\n" +
            "    from sessiondetail sd, sessions s, students st, instructors i\n" +
            "    where s.id=sd.sessionid and sd.studentid = st.id and s.instructorid=i.id and s.id = ?1", nativeQuery = true)
    List<Map<String, Object>> getSessionDetailbySessionId(Integer sessionId);

    @Query(value = " select sd.id id,\n" +
            "    sd.studentid studentId,\n" +
            "    sd.sessionid sessionId,\n" +
            "    sd.notes notes,\n" +
            "    sd.todo todo,\n" +
            "    s.date date,\n" +
            "    s.time time,\n" +
            "    s.duration duration,\n" +
            "    st.fullname studentName,\n" +
            "    i.fullname instructorName\n" +
            "    from sessiondetail sd, sessions s, students st, instructors i\n" +
            "    where s.id=sd.sessionid and sd.studentid = s.id and s.instructorid=i.id and st.id = ?1", nativeQuery = true)
    List<Map<String, Object>> getSessionDetailbyStudentId(Integer studentId);

}
