package com.student.portal.Others;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LevelMapper {

    LevelMapper INSTANCE = Mappers.getMapper(LevelMapper.class);

    Level levelDtoToLevel(LevelDto levelDto);

    LevelDto levelToLevelDto(Level level);
}
