package com.student.portal.License;

import com.student.portal.Student.StudentDto;
import com.student.portal.Student.StudentInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseService implements LicenseInterface{

    private final LicenseRepository repo;

    private final LicenseMapper mapper;
    private StudentInterface studentInterface;
    private StudentDto studentDto;
    private LicenseDto licenseDto;
    private License license;
    private List<StudentDto> studentDtoList;

    public LicenseService(LicenseRepository licenseRepository, LicenseMapper licenseMapper, StudentInterface studentInterface) {
        this.repo = licenseRepository;
        this.mapper = licenseMapper;
        this.studentInterface = studentInterface;
    }

    public List<Map<String, Object>> getallLicenses(){
        return repo.getAllLicenses();
//        return repo.findAll()
//                .stream()
//                .map(this::convertLicenseEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public LicenseDto getLicensebyId(Integer id){
        Optional<License> license = repo.findById(id);
        return convertLicenseEntitytoDTO(license.get());
    }

    public List<LicenseDto> getLicensebyStudentId(Integer id){
        return repo.getLicensebyStudentId(id)
                .stream()
                .map(this::convertLicenseEntitytoDTO)
                .collect(Collectors.toList());
    }

    private LicenseDto convertLicenseEntitytoDTO(License license){
        LicenseDto licenseDto = mapper.licenseToLicenseDto(license);
//        StudentDto studentDto = studentInterface.getStudentbyId(licenseDto.getStudentId());
//        licenseDto.setStudentName(studentDto.getFullName());
//        long remainingdays =0;
//        if (license.getExpirydate().isAfter( LocalDate.now())){
//            licenseDto.setStatus("Valid");
//            remainingdays = ChronoUnit.DAYS.between(LocalDate.now(),license.getExpirydate());
//            licenseDto.setRemainingdays(remainingdays);
//        }
//        else {
//            licenseDto.setStatus("Expired");
//            licenseDto.setRemainingdays(0);
//             }
        return licenseDto;
    }

    public Integer CreateLicense(LicenseDto licenseDto){
        License license = mapper.licenseDtoToLicense(licenseDto);
        if (license.getPurchasedate().toString().length()!=0 && license.getPurchasedate() !=null){
        license.setExpirydate(license.getPurchasedate().plusDays(365));}
        repo.saveAndFlush(license);
        return repo.getMaxLicenseId();
    }

    public boolean DeleteLicensebyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public LicenseDto UpdateLicenseFields(Map<String, Object> licenseDto, Integer id){
        License license = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("license not found on :: "+ id));

        if (licenseDto.containsKey("licensekey") )
            license.setLicensekey(licenseDto.get("licensekey").toString());
        if (licenseDto.containsKey("regid") )
            license.setRegid(licenseDto.get("regid").toString());
        if (licenseDto.containsKey("amount") )
            license.setAmount(Double.valueOf(licenseDto.get("amount").toString()));
        if (licenseDto.containsKey("studentId") )
            license.setStudentId(Integer.parseInt(licenseDto.get("studentId").toString()));
        if (licenseDto.containsKey("expirydate") && licenseDto.get("expirydate").toString().length()!=0)
            license.setExpirydate(LocalDate.parse(licenseDto.get("expirydate").toString()));
        if (licenseDto.containsKey("purchasedate") && licenseDto.get("purchasedate").toString().length()!=0){
            license.setPurchasedate(LocalDate.parse(licenseDto.get("purchasedate").toString()));
            if ( license.getExpirydate()==null)
            license.setExpirydate(license.getPurchasedate().plusDays(365));}
        repo.saveAndFlush(license);
        return convertLicenseEntitytoDTO(license);
    }


}