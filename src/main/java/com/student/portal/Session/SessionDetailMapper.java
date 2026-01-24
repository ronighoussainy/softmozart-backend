package com.student.portal.Session;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionDetailMapper {
    SessionDetailMapper INSTANCE = Mappers.getMapper(SessionDetailMapper.class);

    SessionDetail sessionDetailDtoToSessionDetail(SessionDetailDto sessionDetailDto);

    SessionDetailDto sessionDetailToSessionDetailDto(SessionDetail sessionDetail);
}
