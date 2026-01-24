package com.student.portal.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query(value = "SELECT max(id) FROM Session")
    Integer getMaxSessionId();

    @Query(value = "SELECT u FROM Session u where u.students like %?1% order by u.date desc")
    List<Session> getSessionbyStudentId(Integer studentId);

    @Query(value = "SELECT * FROM sessions S\n" +
            "    where adddate > DATE_SUB(CURDATE(), INTERVAL 1 DAY)", nativeQuery = true)
    List<Session> getSessionsAdded();

    @Query(value = "select s.*, concat(i.firstname,' ',i.lastname) instructorName \n" +
            "from sessions s, instructors i\n" +
            "where s.instructorid = i.id", nativeQuery = true)
    List<Map<String, Object>> getAllSessions();

    @Query(value = "select month,\n" +
            "sum(CASE WHEN s.instructorid='1' THEN TotalSessions ELSE 0 END) as LaraTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='1' THEN TotalStudents ELSE 0 END) as LaraTotalStudents,\n" +
            "sum(CASE WHEN s.instructorid='2' THEN TotalSessions ELSE 0 END) as YaraTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='2' THEN TotalStudents ELSE 0 END) as YaraTotalStudents,\n" +
            "sum(CASE WHEN s.instructorid='3' THEN TotalSessions ELSE 0 END) as LynnTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='3' THEN TotalStudents ELSE 0 END) as LynnTotalStudents\n" +
            "from SessionsSummary s\n" +
            "group by month order by month desc", nativeQuery = true)
    List<Map<String, Object>> getSessionSummarybyMonth();

    @Query(value = "select month,WeekNumber,\n" +
            "sum(CASE WHEN s.instructorid='1' THEN TotalSessions ELSE 0 END) as LaraTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='1' THEN TotalStudents ELSE 0 END) as LaraTotalStudents,\n" +
            "sum(CASE WHEN s.instructorid='2' THEN TotalSessions ELSE 0 END) as YaraTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='2' THEN TotalStudents ELSE 0 END) as YaraTotalStudents,\n" +
            "sum(CASE WHEN s.instructorid='3' THEN TotalSessions ELSE 0 END) as LynnTotalSessions,\n" +
            "sum(CASE WHEN s.instructorid='3' THEN TotalStudents ELSE 0 END) as LynnTotalStudents\n" +
            "from SessionsSummary s\n" +
            "group by month,WeekNumber\n" +
            "order by month desc,WeekNumber desc", nativeQuery = true)
    List<Map<String, Object>> getSessionSummarybyWeek();


}