package com.student.portal.Instructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query(value = "SELECT max(id) FROM Instructor ")
    Integer getMaxInstructorId();
    @Query("SELECT CONCAT(u.firstName, ' ', u.lastName) FROM Instructor u WHERE u.id = :id")
    String getInstructorNameByInstructorId(@Param("id") Integer id);
}
