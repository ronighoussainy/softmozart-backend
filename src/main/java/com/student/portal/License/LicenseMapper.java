package com.student.portal.License;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LicenseMapper {
    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

    License licenseDtoToLicense(LicenseDto licenseDto);

    LicenseDto licenseToLicenseDto(License license);
}
