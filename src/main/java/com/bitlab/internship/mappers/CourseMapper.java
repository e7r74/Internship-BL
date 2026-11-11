package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "name", target = "CourseName")
    CourseDto toDto(Course course);

    @Mapping(source = "CourseDto", target = "name")
    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courses);

}
