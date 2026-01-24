package com.student.portal.Instructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query(value = "SELECT max(id) FROM Instructor ")
    Integer getMaxInstructorId();
}
