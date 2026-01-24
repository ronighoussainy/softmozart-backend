package com.student.portal.Others;

import java.util.List;
import java.util.Map;

public interface CountryInterface {

    public List<CountryDto> getallCountries();

    public CountryDto getCountrybyId(Integer id);

    public Integer CreateCountry(CountryDto studentDto);

    public boolean DeleteCountrybyId(Integer id);

    public CountryDto UpdateCountryFields(Map<String, Object> studentDto, Integer id);

}
