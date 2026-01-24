package com.student.portal.License;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    @Query(value = "SELECT max(id) FROM License")
    Integer getMaxLicenseId();

    @Query(value = "SELECT u FROM License u where u.studentId=?1 order by u.purchasedate desc")
    List<License> getLicensebyStudentId(Integer studentId);

    @Query(value = "select  l.id id,\n" +
            "    l.studentId studentId,\n" +
            "    l.licensekey licensekey,\n" +
            "    l.regid regid,\n" +
            "    l.purchasedate purchasedate,\n" +
            "    l.expirydate expirydate,\n" +
            "    s.fullname studentName,\n" +
            "    l.amount amount,\n" +
            "    (case when datediff(expirydate,sysdate())> 0 then datediff(expirydate,sysdate()) else 0 end ) remainingdays,\n" +
            "     (case when datediff(expirydate,sysdate())> 0 then 'Valid' else 'Expired' end ) status\n" +
            "   from Licenses l, students s\n" +
            "   where l.studentid=s.id", nativeQuery = true)
    List<Map<String, Object>> getAllLicenses();
}