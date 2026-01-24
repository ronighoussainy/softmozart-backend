package com.student.portal.Instructor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    Instructor instructorDtoToInstructor(InstructorDto instructorDto);

    InstructorDto instructorToInstructorDto(Instructor instructor);
}
