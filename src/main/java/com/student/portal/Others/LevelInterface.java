package com.student.portal.Others;

import java.util.List;
import java.util.Map;

public interface LevelInterface {

    public List<LevelDto> getallLevels();

    public LevelDto getLevelbyId(Integer id);

    public Integer CreateLevel(LevelDto studentDto);

    public boolean DeleteLevelbyId(Integer id);

    public LevelDto UpdateLevelFields(Map<String, Object> studentDto, Integer id);

}
