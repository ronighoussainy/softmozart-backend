package com.student.portal.Others;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService implements CountryInterface{

    private final CountryRepository repo;

    private final CountryMapper mapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.repo = countryRepository;
        this.mapper = countryMapper;
    }

    public List<CountryDto> getallCountries(){
        return repo.findAll()
                .stream()
                .map(this::convertCountryEntitytoDTO)
                .collect(Collectors.toList());
    }

    public CountryDto getCountrybyId(Integer id){
        Optional<Country> country = repo.findById(id);
        return convertCountryEntitytoDTO(country.get());
    }


    private CountryDto convertCountryEntitytoDTO(Country country){
        CountryDto countryDto = mapper.countryToCountryDto(country);
        return countryDto;
    }

    public Integer CreateCountry(CountryDto countryDto){
        Country country = mapper.countryDtoToCountry(countryDto);
        repo.saveAndFlush(country);
        return repo.getMaxCountryId();
    }

    public boolean DeleteCountrybyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public CountryDto UpdateCountryFields(Map<String, Object> countryDto, Integer id){
        Country country = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("country not found on :: "+ id));

        if (countryDto.containsKey("name") )
            country.setName(countryDto.get("name").toString());
        repo.saveAndFlush(country);
        return convertCountryEntitytoDTO(country);
    }

}
