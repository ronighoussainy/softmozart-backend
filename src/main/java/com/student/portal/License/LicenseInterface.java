package com.student.portal.License;

import java.util.List;
import java.util.Map;

public interface LicenseInterface {
    public List<Map<String, Object>> getallLicenses();

    public LicenseDto getLicensebyId(Integer id);

    public List<LicenseDto> getLicensebyStudentId(Integer id);

    public Integer CreateLicense(LicenseDto licenseDto);

    public boolean DeleteLicensebyId(Integer id);

    public LicenseDto UpdateLicenseFields(Map<String, Object> licenseDto, Integer id);
}
