package com.student.portal.Session;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    Session sessionDtoToSession(SessionDto sessionDto);

    SessionDto sessionToSessionDto(Session session);
}
