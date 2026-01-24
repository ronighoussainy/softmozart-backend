package com.student.portal.Others;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabService implements LabInterface{

    private final LabRepository repo;

    private final LabMapper mapper;

    public LabService(LabRepository labRepository, LabMapper labMapper) {
        this.repo = labRepository;
        this.mapper = labMapper;
    }

    public List<LabDto> getallLabs(){
        return repo.findAll()
                .stream()
                .map(this::convertLabEntitytoDTO)
                .collect(Collectors.toList());
    }

    public LabDto getLabbyId(Integer id){
        Optional<Lab> lab = repo.findById(id);
        return convertLabEntitytoDTO(lab.get());
    }


    private LabDto convertLabEntitytoDTO(Lab lab){
        LabDto labDto = mapper.labToLabDto(lab);
        return labDto;
    }

    public Integer CreateLab(LabDto labDto){
        Lab lab = mapper.labDtoToLab(labDto);
        repo.saveAndFlush(lab);
        return repo.getMaxLabId();
    }

    public boolean DeleteLabbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public LabDto UpdateLabFields(Map<String, Object> labDto, Integer id){
        Lab lab = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("lab not found on :: "+ id));

        if (labDto.containsKey("name") )
            lab.setName(labDto.get("name").toString());
        repo.saveAndFlush(lab);
        return convertLabEntitytoDTO(lab);
    }

}
