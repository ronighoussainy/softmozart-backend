package com.student.portal.Others;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    @Query(value = "SELECT max(id) FROM Level ")
    Integer getMaxLevelId();
}
