package com.student.portal.Others;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<Lab, Integer> {
    @Query(value = "SELECT max(id) FROM Lab ")
    Integer getMaxLabId();
}
