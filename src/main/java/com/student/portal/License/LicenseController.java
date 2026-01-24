package com.student.portal.License;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  LicenseController {

    private final LicenseInterface licenseInterface;

    public LicenseController(LicenseInterface licenseInterface) {

        this.licenseInterface = licenseInterface;
    }

    @GetMapping("licenses")
    public List<Map<String, Object>> list() {

        return licenseInterface.getallLicenses();
    }

    @GetMapping("licenses/{id}")
    public LicenseDto findById(@PathVariable Integer id)
    {
        try {
            return licenseInterface.getLicensebyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @GetMapping("licensesbystudent/{id}")
    public List<LicenseDto> getLicensesbyStudent (@PathVariable Integer id) {
        return licenseInterface.getLicensebyStudentId(id);
    }

    @PostMapping("licenses")
    public Integer PostLicense(@RequestBody LicenseDto licenseDto) {
        return licenseInterface.CreateLicense(licenseDto);
    }

    @PatchMapping("licenses/{id}")
    public LicenseDto UpdateLicense(@PathVariable Integer id , @RequestBody Map<String, Object> licenseDto) {
        return licenseInterface.UpdateLicenseFields(licenseDto, id);
    }

    @DeleteMapping("licenses/{id}")
    public boolean DeleteLicensebyId(@PathVariable Integer id) {
        return licenseInterface.DeleteLicensebyId(id);
    }

}
