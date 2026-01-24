package com.student.portal.Others;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LevelService implements LevelInterface{

    private final LevelRepository repo;

    private final LevelMapper mapper;

    public LevelService(LevelRepository levelRepository, LevelMapper levelMapper) {
        this.repo = levelRepository;
        this.mapper = levelMapper;
    }

    public List<LevelDto> getallLevels(){
        return repo.findAll()
                .stream()
                .map(this::convertLevelEntitytoDTO)
                .collect(Collectors.toList());
    }

    public LevelDto getLevelbyId(Integer id){
        Optional<Level> level = repo.findById(id);
        return convertLevelEntitytoDTO(level.get());
    }

    private LevelDto convertLevelEntitytoDTO(Level level){
        LevelDto levelDto = mapper.levelToLevelDto(level);
        return levelDto;
    }

    public Integer CreateLevel(LevelDto levelDto){
        Level level = mapper.levelDtoToLevel(levelDto);
        repo.saveAndFlush(level);
        return repo.getMaxLevelId();
    }

    public boolean DeleteLevelbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public LevelDto UpdateLevelFields(Map<String, Object> levelDto, Integer id){
        Level level = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("level not found on :: "+ id));

        if (levelDto.containsKey("title") )
            level.setTitle(levelDto.get("title").toString());
        if (levelDto.containsKey("description") )
            level.setDescription(levelDto.get("description").toString());
        repo.saveAndFlush(level);
        return convertLevelEntitytoDTO(level);
    }
}
