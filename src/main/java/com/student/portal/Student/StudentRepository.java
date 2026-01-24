package com.student.portal.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT max(id) FROM Student ")
    Integer getMaxStudentId();

    @Query(value = "select s.id id, s.firstname firstName, s.middleName middleName,\n" +
            "    s.lastName lastName,   s.mothersName mothersName,    s.dob dob,    s.nationality nationality,\n" +
            "    s.residence residence,    s.email email,    s.phone phone,    s.address address,    s.levelID levelID,\n" +
            "    s.instructorID instructorID,s.language language, \n" +
            "    s.labID labID,s.photo photo,  s.youtube youtube,\n" +
            "    s.facebook facebook,\n" +
            "    s.instagram instagram,\n" +
            "    s.fees fees,\n" +
            "    s.sessionsperweek sessionsperweek,\n" +
            "    s.billing billing,\n" +
            "    s.status status, s.fullname fullName, ins.fullname instructorName,labs.name labName, l.title LevelName , DATEDIFF(SYSDATE(),s.dob)/365 age\n" +
            " from students s, instructors ins, labs , levels l\n" +
            "where s.instructorid = ins.id and s.labid = labs.id and s.levelid = l.id", nativeQuery = true)
    List<Map<String, Object>> getAllStudents();

    @Query(value = "SELECT i.firstname,l.name,count(1) Students, \n" +
            "sum(fees * sessionsperweek) ExpectedMonthlyFees\n" +
            " FROM students s, labs l, instructors i\n" +
            "where s.labid = l.id and s.instructorID=i.id and  status='Active'\n" +
            "group by i.firstname,l.name", nativeQuery = true)
    List<Map<String, Object>> getActiveStudentsSummary();

    @Query(value = "select monthofyear,i.firstname,l.name,count(distinct s.id) Students,\n" +
            " sum(inv.amount) TotalFees\n" +
            "from  students s, labs l, instructors i, invoices inv\n" +
            "where inv.student_id = s.id and s.labid = l.id and s.instructorID=i.id and inv.amount>0 \n" +
            "group by  monthofyear,i.firstname,l.name order by monthofyear desc", nativeQuery = true)
    List<Map<String, Object>> getStudentsSummary();

    @Query(value = "select * from students where\n" +
            "DATE_FORMAT(dob,'%m-%d') = DATE_FORMAT(NOW(),'%m-%d')", nativeQuery = true)
    List<Student> getStudentBirthdays();

    @Query("SELECT  u.fullName as fullName, u.email as email FROM Student u WHERE u.id = :id")
   Map<String,String>  getStudentData(@Param("id") Integer id);
}
