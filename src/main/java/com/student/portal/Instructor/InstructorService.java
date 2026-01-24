package com.student.portal.Instructor;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorService implements InstructorInterface{

    private final InstructorRepository repo;

    private final InstructorMapper mapper;

    public InstructorService(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.repo = instructorRepository;
        this.mapper = instructorMapper;
    }

    public List<InstructorDto> getallInstructors(){
        return repo.findAll()
                .stream()
                .map(this::convertInstructorEntitytoDTO)
                .collect(Collectors.toList());
    }

    public InstructorDto getInstructorbyId(Integer id){
        Optional<Instructor> instructor = repo.findById(id);
        if (instructor.isPresent()) {
            return convertInstructorEntitytoDTO(instructor.get());
        }
        else
            return new InstructorDto();
    }


    private InstructorDto convertInstructorEntitytoDTO(Instructor instructor){
        InstructorDto instructorDto = mapper.instructorToInstructorDto(instructor);
        instructorDto.setFullName(instructorDto.getFirstName()+' '+instructorDto.getLastName());
        return instructorDto;
    }

    public Integer CreateInstructor(InstructorDto instructorDto){
        Instructor instructor = mapper.instructorDtoToInstructor(instructorDto);
        repo.saveAndFlush(instructor);
        return repo.getMaxInstructorId();
    }

    public boolean DeleteInstructorbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public InstructorDto UpdateInstructorFields(Map<String, Object> instructorDto, Integer id){
        Instructor instructor = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("instructor not found on :: "+ id));

        if (instructorDto.containsKey("firstName") )
            instructor.setFirstName(instructorDto.get("firstName").toString());
        if (instructorDto.containsKey("lastName") )
            instructor.setLastName(instructorDto.get("lastName").toString());
        if (instructorDto.containsKey("email") )
            instructor.setEmail(instructorDto.get("email").toString());
        if (instructorDto.containsKey("phone") )
            instructor.setPhone(instructorDto.get("phone").toString());
        if (instructorDto.containsKey("address") )
            instructor.setAddress(instructorDto.get("address").toString());
        repo.saveAndFlush(instructor);
        return convertInstructorEntitytoDTO(instructor);
    }

}
