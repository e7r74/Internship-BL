package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "name", target = "courseName")
    CourseDto toDto(Course course);

    @Mapping(source = "courseName", target = "name")
    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "courseName", target = "name")
    void updateEntityFromDto(CourseDto courseDto, @MappingTarget Course entity);
}
