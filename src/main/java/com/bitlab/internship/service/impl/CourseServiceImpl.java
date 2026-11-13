package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.mappers.CourseMapper;
import com.bitlab.internship.model.Course;
import com.bitlab.internship.repository.CourseRepository;
import com.bitlab.internship.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getAllCourse() {
        log.info("Fetching all Course");
        List<Course> courses= courseRepository.findAll();
        List<CourseDto> courseDtos=courseMapper.toDtoList(courses);
        return courseDtos;
    }

    @Override
    public CourseDto getByIdCourse(Long id) {
       log.info("Fetching course by id: {}",id);
       Course course= courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found with id:"+id));
       return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
       log.info("Create new course");
       Course entity= courseMapper.toEntity(courseDto);
       log.debug("Saving course object: {}",entity);
       Course createCourse= courseRepository.save(entity);
       return courseMapper.toDto(createCourse);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.info("Update course with id: {}",id);
        Course updCourse=checkCourse(id);
        updCourse.setName(courseDto.getCourseName());
        updCourse.setDescription(courseDto.getDescription());
        log.debug("Updating course object: {}",updCourse);
        return courseMapper.toDto(courseRepository.save(updCourse));
    }

    @Override
    public boolean deleteCourse(Long id) {
        log.info("Deleting course with id: {}",id);
        Course course=checkCourse(id);
        courseRepository.delete(course);
        return true;
    }
    private Course checkCourse(Long id){
        return courseRepository.findById(id)
                                .orElseThrow(()->{
                                    log.error("Course not found with id: {}", id);
                                    return new IllegalArgumentException("Course not found with id: "+id);
                                });
    }
}
