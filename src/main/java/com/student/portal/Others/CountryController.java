package com.student.portal.Others;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  CountryController {

    private final CountryInterface countryInterface;

    public CountryController(CountryInterface countryInterface) {

        this.countryInterface = countryInterface;
    }

    @GetMapping("countries")
    public List<CountryDto> list() {

        return countryInterface.getallCountries();
    }

    @GetMapping("countries/{id}")
    public CountryDto findById(@PathVariable Integer id)
    {
        try {
            return countryInterface.getCountrybyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("countries")
    public Integer PostCountry(@RequestBody CountryDto countryDto) {
        return countryInterface.CreateCountry(countryDto);
    }

    @PatchMapping ("countries/{id}")
    public CountryDto UpdateCountry(@PathVariable Integer id ,@RequestBody Map<String, Object> countryDto) {
        return countryInterface.UpdateCountryFields(countryDto, id);
    }

    @DeleteMapping("countries/{id}")
    public boolean DeleteCountrybyId(@PathVariable Integer id) {

        return countryInterface.DeleteCountrybyId(id);
    }

}
