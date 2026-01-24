package com.student.portal.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT max(id) FROM User ")
    Integer getMaxUserId();

    Optional<User> findByUsername(String username);

    @Query(value = " select u.id id,\n" +
            "    u.username username,\n" +
            "    u.password password,\n" +
            "    u.dateupdated dateupdated,\n" +
            "    u.role role,\n" +
            "    u.studentId studentId,\n" +
            "    (case when u.role = 'Admin' then 'All' else s.fullname end) studentName\n" +
            "    from users u LEFT OUTER JOIN students s\n" +
            "    on u.studentid = s.id", nativeQuery = true)
    List<Map<String, Object>> getAllUsers();

}
