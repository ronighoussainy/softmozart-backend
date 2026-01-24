package com.student.portal.Others;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LabMapper {

    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    Lab labDtoToLab(LabDto labDto);

    LabDto labToLabDto(Lab lab);
}
