package com.student.portal.Others;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    Country countryDtoToCountry(CountryDto countryDto);

    CountryDto countryToCountryDto(Country country);
}
