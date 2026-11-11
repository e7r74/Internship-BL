package com.bitlab.internship.service;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CourseService {

    List<CourseDto> getAllCourse();
    CourseDto getByIdCourse(Long id);
    CourseDto createCourse(CourseDto courseDto);
    boolean deleteCourse(Long id);
}
